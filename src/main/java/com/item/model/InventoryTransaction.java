package com.item.model;

import lombok.Data;

import java.util.Date;

/**
 * 库存表
 */
@Data
public class InventoryTransaction {
    /**
     * Id主键
     */
    private Long id;

    /**
     * 商品号码
     */
    private String itemNumber;

    /**
     * 商品库存数量
     */
    private Integer quantity;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 编辑时间
     */
    private Date editTIme;

    /**
     * 编辑用户
     */
    private String editUser;
}