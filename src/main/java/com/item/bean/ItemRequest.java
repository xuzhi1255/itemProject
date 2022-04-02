package com.item.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @Description : 增加请求类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 15:10
 **/
@Data
public class ItemRequest {

    /**
     * 商品号码
     */
    @NotBlank(message = "itemNumber不能为空")
    private String itemNumber;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 1为启用,0为禁用
     */
    @NotNull(message = "status不能为空")
    private Byte status;

    /**
     * 创建时间
     */
    @NotNull(message = "createTime不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 编辑时间
     */
    @NotNull(message = "editTime不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    /**
     * 编辑用户
     */
    @NotBlank(message = "editUser不能为空")
    private String editUser;
}
