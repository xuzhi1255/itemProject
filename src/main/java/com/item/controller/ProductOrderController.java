package com.item.controller;

import com.item.bean.CommonReply;
import com.item.bean.ProductOrder;
import com.item.enums.HttpStatusEnum;
import com.item.service.ProductOrderService;
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
    ProductOrderService productOrderService;

    /**
     * @Description : TODO
     * @Author : Zhilin_Xu
     * @Date : 2022/4/8 10:24
     **/
    @PostMapping("/receving")
    @ResponseBody
    public CommonReply receviePo(@RequestBody @Validated ProductOrder productOrder) throws Exception {
        String msg = productOrderService.receviePo(productOrder);
        return CommonUtils.buildResp(HttpStatusEnum.SUCCESS.getCode(), msg, null);
    }

}
