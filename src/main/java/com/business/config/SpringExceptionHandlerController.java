package com.business.config;

import com.jokers.common.message.ResultMessage;
import com.jokers.common.response.IResult;
import com.jokers.common.response.IResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
    public IResult exceptionHandler(Exception e) {
        log.error(ResultMessage.INTERNAL_SERVER_ERROR.getMsg(), e.getMessage(), e);
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            String file = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getField();
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, file + message);
        }
        return IResultUtil.errorResult(ResultMessage.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
}
