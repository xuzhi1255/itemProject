package com.item.controller;

import com.item.bean.CommonReply;
import com.item.bean.PurchaseOrder;
import com.item.enums.HttpStatusEnum;
import com.item.service.PurchaseOrderService;
import com.item.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description : 采购单相关控制器
 * @Author : Zhilin_Xu
 * @Date : 2022/4/8 10:22
 **/
@Controller
@RequestMapping("/po")
public class ProductOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    /**
     * @Description : TODO
     * @Author : Zhilin_Xu
     * @Date : 2022/4/8 10:24
     **/
    @PostMapping("/receving")
    @ResponseBody
    public CommonReply receviePo(@RequestBody @Validated PurchaseOrder purchaseOrder) throws Exception {
        purchaseOrderService.receviePo(purchaseOrder);
        return CommonUtils.buildResp(HttpStatusEnum.SUCCESS.getCode(), "采购单处理成功", null);
    }

}
