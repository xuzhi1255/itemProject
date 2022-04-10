package com.item.service;

import com.item.bean.ProductOrder;

/**
 * @Description : 采购单服务类
 * @Author :Zhilin_Xu
 * @Date :2022/4/8 10:49
 **/

public interface ProductOrderService {

    /**
     * @Description : 接受采购单信息执行入库操作
     * @Author : Zhilin_Xu
     * @Date : 2022/4/8 10:52
     **/
    public String receviePo(ProductOrder productOrder);

}
