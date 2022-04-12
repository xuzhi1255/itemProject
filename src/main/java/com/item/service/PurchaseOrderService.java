package com.item.service;

import com.item.bean.PurchaseOrder;
import com.item.message.PoReceiveMessage;

/**
 * @Description : 采购单服务类
 * @Author :Zhilin_Xu
 * @Date :2022/4/8 10:49
 **/

public interface PurchaseOrderService {

    /**
     * @Description : 接受采购单信息执行入库操作
     * @Author : Zhilin_Xu
     * @Date : 2022/4/8 10:52
     **/
    void receviePo(PurchaseOrder purchaseOrder) throws Exception;

    /**
     * @Description :处理采购单回推信息
     * @Author : Zhilin_Xu
     * @Date : 2022/4/11 11:14
     **/
    void processPoMessage(PoReceiveMessage poReceiveMessage);

}
