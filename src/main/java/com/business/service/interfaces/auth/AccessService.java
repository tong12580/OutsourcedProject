package com.business.service.interfaces.auth;


import com.jokers.common.response.IResult;

/**
 * 访问服务
 * AccessService
 * Created by yuTong on 20180223.
 */
public interface AccessService {

    /**
     * 注册服务
     *
     * @param username 账户
     * @param password 密码
     * @param role     权限
     * @return IResult
     */
    IResult<String> registered(String username, String password, String role);
    
}
