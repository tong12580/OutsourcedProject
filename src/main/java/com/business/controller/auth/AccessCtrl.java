package com.business.controller.auth;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.service.interfaces.auth.AccessService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author yutong
 * @version 1.0
 * @description 访问控制器
 * @since 2018/2/22 23:35
 */
@RestController
@Api(value = "访问控制器", tags = {"访问控制器"}, description = "注册")
public class AccessCtrl {

    @Resource
    private AccessService accessService;

    /**
     * 注册
     *
     * @param username 账号
     * @param password 密码
     * @return IResult
     */
    @ApiOperation(value = "注册", notes = "使用用户名、密码以及权限名称注册用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true),
            @ApiImplicitParam(name = "role", value = "权限名称", dataType = "String")
    })
    @PostMapping("/registered")
    public IResult<String> registered(String username, String password, String role) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "username or password");
        }
        return accessService.registered(username, password, role);
    }
}
