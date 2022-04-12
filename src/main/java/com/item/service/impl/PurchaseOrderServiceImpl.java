package com.item.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.item.bean.ItemInfo;
import com.item.bean.PurchaseOrder;
import com.item.dao.InventoryTransactionMapper;
import com.item.dao.ItemMapper;
import com.item.dao.PoMasterMapper;
import com.item.dao.PoTransactionMapper;
import com.item.message.PoReceiveMessage;
import com.item.model.InventoryTransaction;
import com.item.model.PoMaster;
import com.item.model.PoTransaction;
import com.item.mq.MessageService;
import com.item.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description : 订购单服务实现类
 * @Author : 24755
 * @Date : 2022/4/10 10:56
 **/
@Service
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    PoMasterMapper poMasterMapper;

    @Autowired
    PoTransactionMapper poTransactionMapper;

    @Autowired
    InventoryTransactionMapper inventoryTransactionMapper;

    @Autowired
    MessageService messageService;

    /**
     * @Description : 接受采购单信息执行入库操作
     * @Author : Zhilin_Xu
     * @Date : 2022/4/8 10:52
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void receviePo(PurchaseOrder purchaseOrder) throws Exception {
        //检查poNumber是否已经存在
        int poCount = poMasterMapper.countByPoNumber(purchaseOrder.getPoNumber());
        if (poCount > 0) {
            log.info("采购单订单号已经存在:{}", purchaseOrder.getPoNumber());
            throw new Exception("采购单订单号已经在存在");
        }
        //入表PoMaster
        PoMaster poMaster = createPoMasterModel(purchaseOrder);
        poMasterMapper.insert(poMaster);
        //入表POTransaction
        for (ItemInfo itemInfo : purchaseOrder.getItemInfos()) {
            //查询itemNumber是否入库
            if (itemMapper.countByItemNumber(itemInfo.getItemNumber()) == 0) {
                log.info("商品号码: {} 没有创建，无法进行采购单入库操作", itemInfo.getItemNumber());
                throw new Exception(itemInfo.getItemNumber() + "信息没有找到，请先创建商品");
            } else {
                PoTransaction poTransaction = createPoTransactionModel(itemInfo, purchaseOrder.getPoNumber());
                poTransactionMapper.insert(poTransaction);
            }
        }

        //创建异步消息发送至队列
        PoReceiveMessage poReceiveMessage = createPoReceiveMessage(purchaseOrder);
        messageService.sendMessage("PO_RECEIVE_QUEUE", buildMessage(poReceiveMessage));

    }

    /**
     * @Description :处理采购单回推信息
     * @Author : Zhilin_Xu
     * @Date : 2022/4/11 11:14
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void processPoMessage(PoReceiveMessage poReceiveMessage) {
        String editUser = poReceiveMessage.getEditUser();
        //查询InventoryTransaction表，有则更新，没有则重新插入数据
        InventoryTransaction inventoryTransactionModel = null;
        List<ItemInfo> itemInfoList = poReceiveMessage.getItemInfos();
        for (ItemInfo itemInfo : itemInfoList) {
            InventoryTransaction inventoryTransactionRecord =
                    inventoryTransactionMapper.selectOneByItemNumber(itemInfo.getItemNumber());
            if (ObjectUtil.isNull(inventoryTransactionRecord)) {
                inventoryTransactionModel = createInventoryTransactionInsert(itemInfo, editUser);
                inventoryTransactionMapper.insert(inventoryTransactionModel);
            } else {
                inventoryTransactionModel = createInventoryTransactionUpdate(itemInfo, editUser,
                        inventoryTransactionRecord.getId());
                inventoryTransactionMapper.updateByPrimaryKeySelective(inventoryTransactionModel);
            }

        }
    }


    private PoMaster createPoMasterModel(PurchaseOrder purchaseOrder) {
        PoMaster poMaster = new PoMaster();
        BeanUtil.copyProperties(purchaseOrder, poMaster);
        poMaster.setEditTime(DateUtil.date());
        return poMaster;
    }

    private PoTransaction createPoTransactionModel(ItemInfo itemInfo, String poNumber) {
        PoTransaction poTransaction = new PoTransaction();
        poTransaction.setPoNumber(poNumber);
        poTransaction.setCost(itemInfo.getCost());
        poTransaction.setItemNumber(itemInfo.getItemNumber());
        poTransaction.setQuantity(itemInfo.getQuantity());
        return poTransaction;
    }

    private PoReceiveMessage createPoReceiveMessage(PurchaseOrder purchaseOrder) {
        PoReceiveMessage poReceiveMessage = new PoReceiveMessage();
        BeanUtil.copyProperties(purchaseOrder, poReceiveMessage);
        return poReceiveMessage;
    }

    private InventoryTransaction createInventoryTransactionInsert(ItemInfo itemInfo, String editUser) {
        InventoryTransaction inventoryTransaction = new InventoryTransaction();
        BeanUtil.copyProperties(itemInfo, inventoryTransaction);
        inventoryTransaction.setEditUser(editUser);
        inventoryTransaction.setEditTIme(DateUtil.date());
        inventoryTransaction.setCreateTime(DateUtil.date());
        return inventoryTransaction;
    }

    private Message buildMessage(PoReceiveMessage poReceiveMessage) {
        Message message = MessageBuilder.withBody(JSONUtil.toJsonStr(poReceiveMessage).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentType("UTF-8")
                .setMessageId(poReceiveMessage.getPoNumber()).build();
        return message;
    }

    private InventoryTransaction createInventoryTransactionUpdate(ItemInfo itemInfo, String editUser,
                                                                  Long transactionId) {
        InventoryTransaction inventoryTransaction = new InventoryTransaction();
        inventoryTransaction.setQuantity(itemInfo.getQuantity());
        inventoryTransaction.setEditUser(editUser);
        inventoryTransaction.setEditTIme(DateUtil.date());
        inventoryTransaction.setId(transactionId);
        return inventoryTransaction;
    }
}
