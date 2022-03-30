package com.item.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 编辑时间
 */
@Getter
@Setter
@ToString
public class Item {
    /**
     * itemId
     */
    @ExcelProperty(value = {"商品id"}, index = 0)
    private Long id;

    /**
     * 商品号码
     */
    @ExcelProperty(value = {"商品号码"}, index = 1)
    private String itemNumber;

    /**
     * 商品描述
     */
    @ExcelProperty(value = {"商品描述"}, index = 2)
    private String description;

    /**
     * 1为启用,0为禁用
     */
    @ExcelProperty(value = {"商品状态"}, index = 3)
    private Byte status;

    /**
     * 创建时间
     */
    @ExcelProperty(value = {"创建时间"}, index = 4)
    private Date createTime;

    /**
     * 编辑时间
     */
    @ExcelProperty(value = {"编辑时间"}, index = 5)
    private Date editTime;

    /**
     * 编辑用户
     */
    @ExcelProperty(value = {"编辑用户"}, index = 6)
    private String editUser;
}