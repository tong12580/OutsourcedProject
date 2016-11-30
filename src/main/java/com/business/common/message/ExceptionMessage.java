package com.business.common.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by yuton on 2016/8/8.
 */
public enum ExceptionMessage {

    SPLIT_PARAMETERS_EXCEPTION(0001, "拆分参数异常: {}"),
    IO_EXCEPTION(0002, "IO异常: {}"),
    MALFORMED_URL_EXCEPTION(HttpStatus.URI_TOO_LONG.value(), "获取URL异常: {}"),
    TO_BIGDECIMAL_EXCEPTION(0004, "数值转换异常: {}"),
    CONNECT_TIME_OUT(HttpStatus.REQUEST_TIMEOUT.value(),"连接超时: {}"),
    NUMBER_FORMAT_EXCEPTION(0006,"格式化数据异常: {}");

    @Getter
    Integer exceptionCode;
    @Getter
    String exceptionMsg;

    ExceptionMessage(int exceptionCode, String exceptionMsg) {
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }
}
