package com.business.controller;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

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
        return IResultUtil.errorResult(ResultMessage.STATUS_FAILURE, e.getMessage()).toJson();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
}
