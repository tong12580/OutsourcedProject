package com.business.controller.auth;

import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yutong
 * @version 1.0
 * @description 访问控制器
 * @since 2018/2/22 23:35
 */
@RestController
public class AccessCtrl {

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
}
