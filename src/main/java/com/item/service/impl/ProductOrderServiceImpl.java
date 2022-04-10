package com.item.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.item.bean.ItemInfo;
import com.item.bean.ProductOrder;
import com.item.dao.InventoryTransactionMapper;
import com.item.dao.ItemMapper;
import com.item.dao.PoMasterMapper;
import com.item.dao.PoTransactionMapper;
import com.item.model.PoMaster;
import com.item.model.PoTransaction;
import com.item.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description : 订购单服务实现类
 * @Author : 24755
 * @Date : 2022/4/10 10:56
 **/
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    PoMasterMapper poMasterMapper;

    @Autowired
    PoTransactionMapper poTransactionMapper;

    @Autowired
    InventoryTransactionMapper inventoryTransactionMapper;

    /**
     * @Description : 接受采购单信息执行入库操作
     * @Author : Zhilin_Xu
     * @Date : 2022/4/8 10:52
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String receviePo(ProductOrder productOrder) {
        StringBuilder stringBuilder = new StringBuilder();
        //入表PoMaster
        PoMaster poMaster = createPoMasterModel(productOrder);
        poMasterMapper.insert(poMaster);
        //入表POTransaction
        for (ItemInfo itemInfo : productOrder.getItemInfos()) {

            //查询itemNumber是否入库
            if (itemMapper.countByItemNumber(itemInfo.getItemNumber()) == 0) {
                continue;
            } else {
                PoTransaction poTransaction = createPoTransactionModel(itemInfo, productOrder.getPoNumber());
                poTransactionMapper.insert(poTransaction);
            }
        }

        //创建异步消息发送至队列

        return stringBuilder.toString();
    }


    private PoMaster createPoMasterModel(ProductOrder productOrder) {
        PoMaster poMaster = new PoMaster();
        BeanUtil.copyProperties(productOrder, poMaster);
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
}
