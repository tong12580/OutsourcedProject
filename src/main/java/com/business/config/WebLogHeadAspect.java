package com.business.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jokers.common.response.IResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * WebLogHeadAspect
 * Created by yuTong on 2018/2/3.
 */
@Slf4j
@Aspect
@Order(1)
@Configuration
public class WebLogHeadAspect {


    @Pointcut("execution(public * com.business.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        if (null != joinPoint.getArgs() && joinPoint.getArgs().length > 0
                && joinPoint.getArgs()[0] instanceof ExtendedServletRequestDataBinder) {
            return;
        }
        if (null != joinPoint.getArgs() && joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof MultipartFile) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        log.info("URL: {}, Method: {}, params: {}", request.getRequestURL(),
                request.getMethod(), JSON.toJSON(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(IResult ret) throws JsonProcessingException {
        if (null != ret) {
            log.info("Result : {}", ret.toJson());
        }
    }
}
