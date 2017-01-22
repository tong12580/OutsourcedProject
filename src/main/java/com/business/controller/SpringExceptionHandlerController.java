package com.business.controller;

import com.business.common.CommonTools;
import com.business.common.message.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by yuton on 2016/11/7.
 */
@ControllerAdvice
public class SpringExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) throws JsonProcessingException {
        return CommonTools.errorResult(ResultMessage.STATUS_FAILURE, e.getMessage()).toJson();
    }
}
