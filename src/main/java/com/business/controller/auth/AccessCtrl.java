package com.business.controller.auth;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;

import com.business.service.interfaces.auth.AccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * @description 访问控制器
 * @since 2018/2/22 23:35
 */
@RestController
public class AccessCtrl {

    @Resource
    private AccessService accessService;

    /**
     * 登录成功返回
     */
    @PostMapping("/loginSuccessful")
    public IResult<String> loginSuccessful() {
        return IResultUtil.successResult();
    }

    /**
     * 登录失败返回
     */
    @PostMapping("/loginFail")
    public IResult<String> loginFail() {
        return IResultUtil.errorResult();
    }


    /**
     * 注册
     *
     * @param username 账号
     * @param password 密码
     * @return IResult
     */
    @PostMapping("/registered")
    public IResult<String> registered(String username, String password, String role) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "username or password");
        }
        return accessService.registered(username, password, role);
    }
}
