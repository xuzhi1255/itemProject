package com.item.model;

import lombok.Data;

import java.util.Date;

/**
 * 采购表主表
 */
@Data
public class PoMaster {
    /**
     * id主键
     */
    private Long id;

    /**
     * 采购单代码
     */
    private String poNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑用户
     */
    private String editUser;
}