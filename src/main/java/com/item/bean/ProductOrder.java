package com.item.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description : 采购单请求体
 * @Author : Zhilin_Xu
 * @Date : 2022/4/8 10:08
 */
@Data
public class ProductOrder {

    // 采购单订单号
    @NotBlank(message = "采购单单号不能为空")
    private String poNumber;

    //创建时间
    @NotNull(message = "createTime不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //编辑用户
    @NotBlank(message = "editUser不能为空")
    private String editUser;

    //商品信息
    @Valid
    @NotEmpty(message = "商品信息不能为空")
    private List<ItemInfo> itemInfos;
}
