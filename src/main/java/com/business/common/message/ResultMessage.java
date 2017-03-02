package com.business.common.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by yuton on 2016/11/6.
 */
public enum ResultMessage {
    DATABASE_NULL(HttpStatus.OK.value(), "数据为空: {}"),
    STATUS_SUCCESS(HttpStatus.OK.value(), "调用成功"),
    STATUS_SUCCESS_ADD(HttpStatus.OK.value(), "添加成功"),
    STATUS_SUCCESS_UPD(HttpStatus.OK.value(), "修改成功"),
    STATUS_SUCCESS_DEL(HttpStatus.OK.value(), "删除成功"),
    STATUS_SUCCESS_GET(HttpStatus.OK.value(), "查询成功"),
    STATUS_UPDATE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "修改{}异常"),
    STATUS_ADD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加{}异常"),
    STATUS_DEL_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除{}异常"),
    STATUS_UPDATE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "拒绝修改: {}"),
    STATUS_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常: {}"),
    INPUT_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST.value(), "参数{}为空"),
    INPUT_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "参数异常,{}"),
    ERROR_PROMPT(HttpStatus.BAD_REQUEST.value(), "错误提示: {}"),
    LOGIN_TIME_OUT(HttpStatus.REQUEST_TIMEOUT.value(), "请先登录!"),
    REQUEST_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST.value(), "请求参数为空"),
    DATABASE_ABNORMAL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "数据库异常: {}"),
    JSON_CONVERSION_TO_BEAN_ABNORMAL(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "json转换异常: {}");
    @Getter
    private int code;
    @Getter
    private String msg;

    ResultMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
