package com.business.config;

import com.business.common.json.JsonUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * WebLogHeadAspect
 * Created by yuTong on 2018/2/3.
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class WebLogHeadAspect {


    @Pointcut("execution(public * com.business.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL: {}, Method: {}, params: {}", request.getRequestURL(),
                request.getMethod(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        log.info("Result : {}", JsonUtil.objectToJson(ret));
    }
}
