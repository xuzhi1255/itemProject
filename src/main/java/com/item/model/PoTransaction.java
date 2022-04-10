package com.item.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PoTransaction {
    private Long id;

    /**
     * 采购单代码
     */
    private String poNumber;

    /**
     * 商品号码
     */
    private String itemNumber;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 采购成本单价
     */
    private BigDecimal cost;
}