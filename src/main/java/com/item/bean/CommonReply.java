package com.item.bean;

import lombok.Data;

/**
 * @Description : 回复类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 15:47
 **/
@Data
public class CommonReply {

    //返回码
    private int code;

    //返回信息
    private String msg;

    //返回数据
    private Object data;

}
