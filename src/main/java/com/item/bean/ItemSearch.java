package com.item.bean;

import lombok.Data;

/**
 * @Description : 商品搜索类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/29 13:51
 **/
@Data
public class ItemSearch {

    /**
     * itemId
     */
    private Long id;

    /**
     * 商品号码
     */
    private String itemNumber;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 1为启用,0为禁用
     */

    private Byte status;

    /**
     * 创建开始时间
     */

    private String createStartTime;

    /**
     * 创建结束时间
     */
    private String createEndTime;


    /**
     * 编辑开始时间
     */
    private String editStartTime;

    /**
     * 编辑介绍时间
     */
    private String editEndTime;

    /**
     * 编辑用户
     */
    private String editUser;

    /**
     * 页数
     */
    private Integer page;
    /**
     * 条数
     */
    private Integer limit;



}
