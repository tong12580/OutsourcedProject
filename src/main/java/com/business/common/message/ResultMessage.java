package com.business.common.message;

import lombok.Getter;

/**
 * Created by yuton on 2016/11/6.
 */
public enum ResultMessage {
    DATABASE_NULL(200, "数据为空"),
    STATUS_SUCCESS(200, "调用成功"),
    STATUS_SUCCESS_ADD(201, "添加成功"),
    STATUS_SUCCESS_UPD(202, "修改成功"),
    STATUS_SUCCESS_DEL(203, "删除成功"),
    STATUS_SUCCESS_GET(204, "查询成功"),
    STATUS_UPDATE_FAILURE(400, "修改异常"),
    STATUS_ADD_FAILURE(401, "添加异常"),
    STATUS_DEL_FAILURE(402, "删除异常"),
    STATUS_UPDATE_EXCEPTION(403, "拒绝修改"),
    STATUS_FAILURE(500, "系统异常"),
    INPUT_PARAMETER_IS_EMPTY(540, "参数为空"),
    REQUEST_PARAMETER_IS_EMPTY(541, "请求参数为空"),
    DATABASE_ABNORMAL(542, "数据库异常"),
    JSON_CONVERSION_TO_BEAN_ABNORMAL(543, "json转换异常");
    @Getter
    private int code;
    @Getter
    private String msg;

    ResultMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
