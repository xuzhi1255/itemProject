package com.item.bean;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
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
    @Min(value = 1, message = "所输入值必须大于0")
    private int quantity;

    //商品原件
    @NotNull(message = "cost不能为空")
    @Digits(integer = 1000, fraction = 2, message = "商品单价不符合标准")
    private BigDecimal cost;
}
