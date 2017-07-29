package com.business.controller;

import com.business.common.CommonTools;
import com.business.common.message.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2016/11/7 18:35
 */
@Slf4j
@ControllerAdvice
public class SpringExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) throws JsonProcessingException {
        log.error(ResultMessage.STATUS_FAILURE.getMsg(), e.getMessage(), e);
        return CommonTools.errorResult(ResultMessage.STATUS_FAILURE, e.getMessage()).toJson();
    }
}
