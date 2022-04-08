package com.item.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description : 商品相关信息
 * @Author : Zhilin_Xu
 * @Date : 2022/4/8 10:18
 **/
@Data
public class ItemInfo {

    //商品号码
    @NotBlank(message = "itemNumber不能为空")
    private String itemNumber;

    //数量
    @NotNull(message = "quantity不能为空")
    private int quantity;

    //商品原件
    @NotNull(message = "cost不能为空")
    private BigDecimal cost;
}
