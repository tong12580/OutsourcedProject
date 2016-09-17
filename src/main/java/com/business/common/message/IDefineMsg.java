package com.business.common.message;

import java.io.Serializable;

/**
 * 消息提示
 * @author yuton
 */
public interface IDefineMsg extends Serializable {

    /**
     * 状态标签  1可用
     */
    String STATUS = "1";
    String MAN = "男";
    String REGISTER_FAIL = "注册失败";
    String SYSTEM_EXCEPTION = "系统异常，请联系管理员";
    String REDIS_EXCEPTION = "redis异常";
    String SQL_DB_EXCEPTION = "数据库访问异常，请稍后重试";
    String ACCOUNT_FREEZED = "您的账号已冻结，请联系管理员";
    String LACK_PARAM = "不能为空";
    String FIND_LACK_PARAM_IS_NOT_NULL = "查询条件不能为空";
    String GET_SUCCESS = "查询成功";
    String DEL_SUCCESS = "删除成功";
    String ADD_SUCCESS = "添加成功";
    String UPD_SUCCESS = "修改成功";
    String DEL_FAIL = "删除失败";
    String ADD_FAIL = "添加失败";
    String UPD_FAIL = "修改失败";
    String GET_FAIL = "查询失败";
    String USE_FAIL = "操作失败";
    String PARAM_EXCEPTION = "参数异常";
    String USE_SUCCESS = "操作成功";
    String CHEACK_SUC = "校验成功";
    String URL_REQEST_ERR = "请求url失败";
    String URL_REQEST_SUC = "请求url成功";
    String PHONE_CODE_SUC = "发送短信验证码";
    String PHONE_CODE_ERR = "发送验证码失败";
    String PHONE_CODE_GET = "请重新获取验证码";
    String PHONE_CODE_INFO = "手机验证码";
    String PHONE_CODE_IS_FAIL = "手机验证码错误";
    String CODE_IS_FAIL = "验证码错误";
    String PHONE_ERR = "手机号码错误";
    String FORMAT_ERR = "格式错误";
    String LOGIN_ERR = "登陆帐号或密码错误";
    String TRANSACTION_PASSWORD_ERR = "交易密码错误";
    String LOGIN_SUC = "登陆成功";
    String TRANSACTION_SUC = "交易成功";
    String LONGIN_PREASE = "请登录";
    String PASSWORD_BY_FORMAT = "密码格式错误";
    String PASSWORD_FAIL = "密码错误";
    String PASSWORD_IS_DIFFRENT = "两次密码输入不一致";
    String ADMIN_USER_KEY = "ADMIN_USER_KEY";
    String USER_KEY = "user_key";
    String CACHE_USER_KEY_FAIL = "缓存用户信息失败";
    String UPLOAD_IMG_FAIL = "上传图片失败";
    String UPLOAD_IMG_SUC = "上传图片成功";

    /************
     * 返回参数类型
     *************/
    int WS_TYPE_NULL = 0;
    int WS_TYPE_INT = 1;
    int WS_TYPE_STRING = 2;
    int WS_TYPE_OBJECT = 3;
    int WS_TYPE_LIST_MAP = 4;
    int WS_TYPE_MAPMAP = 5;
    int WS_TYPE_MULLIST = 6;
    int WS_TYPE_LISTOBJECT = 7;
    int WS_TYPE_STREAM = 8;
    int WS_TYPE_JSON = 9;
    /*********************************/
    /**
     * IResult 错误代码
     */
    String CODE_ERROR = "0";
    /**
     * IResult 正确代码
     */
    String CODE_SUCCESS = "1";
    String KEY_RETURN_CODE = "recode"; // 返回代码，map键定义 // 1：返回成功，0：返回失败
    String KEY_RETURN_MSG = "remsg";// 返回信息，map值定义
}