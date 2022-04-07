package com.item.controller;

import com.item.bean.CommonReply;
import com.item.enums.HttpStatusEnum;
import com.item.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @Description : 统一异常处理器
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 15:56
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 必要参数检验失败异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonReply validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                FieldError fieldError = (FieldError) objectError;
                if (log.isDebugEnabled()) {
                    log.error("Data check failure : object: {},field: {},errorMessage: {}",
                            fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                }
                errorMsg.append(objectError.getDefaultMessage());
                errorMsg.append(",");
            }
            errorMsg = new StringBuilder(errorMsg.substring(0, errorMsg.length() - 1));
        }
        return CommonUtils.buildResp(HttpStatusEnum.BAD_REQUEST.getCode(), "必要参数未填，" + errorMsg, null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CommonReply constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return CommonUtils.buildResp(HttpStatusEnum.BAD_REQUEST.getCode(), "必要参数未填，" + exception.getMessage(), null);
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public CommonReply exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return CommonUtils.buildResp(HttpStatusEnum.REQUEST_FAIL.getCode(), "后端操作失败  " + e.getMessage(), null);
    }
}


