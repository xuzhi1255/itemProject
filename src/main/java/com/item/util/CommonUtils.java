package com.item.util;

import cn.hutool.core.util.StrUtil;
import com.item.bean.CommonReply;

/**
 * @Description : 普通工具类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 15:50
 **/
public class CommonUtils {

    public static CommonReply buildResp(int code, String msg, String data){
        CommonReply commonReply = new CommonReply();
        commonReply.setCode(code);
        commonReply.setMsg(msg);
        if(StrUtil.isNotBlank(data)){
            commonReply.setData(data);
        }
        return commonReply;
    }
}
