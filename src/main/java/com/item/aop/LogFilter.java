package com.item.aop;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Description :记录controller输入输出日志
 * @Author : Zhilin_Xu
 * @Date : 2022/4/2 11:22
 **/


@Aspect
@Component
@Slf4j
public class LogFilter {


    // 统一切点,对controller及其子包中所有的类的所有方法切面
    @Pointcut("execution(public * com.item.controller..*.*(..))")
    public void Pointcut() {
    }

    @Before("Pointcut()")
    public void before(JoinPoint joinPoint) {
        Object args[] = joinPoint.getArgs();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        Method method = sig.getMethod();
        if (null != method.getDeclaringClass().getName() && null != method.getName() && null != args && args.length > 0) {
            log.info("{} . {} : 请求参数：{}", method.getDeclaringClass().getName(), method.getName(), Arrays.toString(args));
        }
    }

    @AfterReturning(value = "Pointcut()", returning = "rvt")
    public void after(JoinPoint joinPoint, Object rvt) {
        MethodSignature sig1 = (MethodSignature) joinPoint.getSignature();
        Method method1 = sig1.getMethod();
        if (null != rvt && null != method1.getDeclaringClass()) {
            try {
                log.info("{} . {} : 返回数据：{}", method1.getDeclaringClass().getName(), method1.getName(),
                        JSONUtil.toJsonStr(rvt));
            } catch (Exception e) {

            }
        }
    }

}