package com.item.message;

import com.item.bean.ItemInfo;
import lombok.Data;

import java.util.List;

/**
 * @Description : 采购单消息类
 * @Author : Zhilin_Xu
 * @Date : 2022/4/11 10:11
 **/
@Data
public class PoReceiveMessage {

    //商品信息
    private List<ItemInfo> itemInfos;

    //编辑用户
    private String editUser;

    //采购单号码
    private String poNumber;
}
