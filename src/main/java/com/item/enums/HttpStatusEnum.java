package com.item.enums;

import cn.hutool.http.HttpStatus;

/**
 * @Description : 返回状态枚举类
 * @Author : Zhilin_Xu
 * @Date : 2022/4/6 11:02
 **/
public enum HttpStatusEnum {
    /**
     * 返回码说明
     */
    SUCCESS(HttpStatus.HTTP_OK),
    BAD_REQUEST(HttpStatus.HTTP_BAD_REQUEST),
    REQUEST_FAIL(HttpStatus.HTTP_INTERNAL_ERROR);

    private final int code;

    HttpStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
