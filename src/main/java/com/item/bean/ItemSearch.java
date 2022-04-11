package com.item.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    /**
     * 创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;


    /**
     * 编辑开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editStartTime;

    /**
     * 编辑介绍时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editEndTime;

    /**
     * 编辑用户
     */
    private String editUser;

    /**
     * 页数
     */
    private Integer page = 0;
    /**
     * 条数
     */
    private Integer limit = 10;

    /**
     * 偏移量
     */
    private Integer offset = 0;


}
