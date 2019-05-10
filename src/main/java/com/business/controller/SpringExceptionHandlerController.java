package com.business.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jokers.common.message.ResultMessage;
import com.jokers.common.response.IResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuton
 * @version 1.0
 * @since 2016/11/7 18:35
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class SpringExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) throws JsonProcessingException {
        log.error(ResultMessage.INTERNAL_SERVER_ERROR.getMsg(), e.getMessage(), e);
        return IResultUtil.errorResult(ResultMessage.INTERNAL_SERVER_ERROR, e.getMessage()).toJson();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
}
