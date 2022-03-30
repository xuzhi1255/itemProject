package com.item.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * @Description : 增加请求类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 15:10
 **/
@Data
public class ItemRequest {

    /**
     * itemId
     */
    private Long id;

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
    @NotBlank(message = "createTime不能为空")
    @Pattern(regexp = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]" +
            "|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$",message = "createTime格式错误")
    private String createTime;

    /**
     * 编辑时间
     */
    @NotBlank(message = "editTime不能为空")
    @Pattern(regexp = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]" +
            "|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$",message = "editTime格式错误")
    private String editTime;

    /**
     * 编辑用户
     */
    @NotBlank(message = "editUser不能为空")
    private String editUser;
}
