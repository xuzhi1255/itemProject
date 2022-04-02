package com.item.util;

import cn.hutool.core.util.ObjectUtil;
import com.item.bean.CommonReply;

/**
 * @Description : 普通工具类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 15:50
 **/
public class CommonUtils {

    public static CommonReply buildResp(int code, String msg, Object data){
        CommonReply commonReply = new CommonReply();
        commonReply.setCode(code);
        commonReply.setMsg(msg);
        if(ObjectUtil.isNotEmpty(data)){
            commonReply.setData(data);
        }
        return commonReply;
    }
}
