package com.business.controller.auth;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.service.interfaces.auth.AuthService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yutong
 * @version 1.0
 * @description 权限控制器
 * @since 2018/2/14 20:35
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AuthCtrl {
    @Resource
    private AuthService authService;

    /**
     * 添加新权限名称
     *
     * @param roleName 权限名称
     * @return IResult
     */
    @PutMapping("/role")
    public IResult<String> addRole(String roleName) {
        if (StringUtils.isEmpty(roleName)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "roleName");
        }
        return authService.addRole(roleName);
    }

    /**
     * 查询权限表
     *
     * @return IResult
     */
    @GetMapping("/roles")
    public IResult queryRoles() {
        return authService.queryRoles();
    }

    /**
     * 修改权限名
     *
     * @param roleId   权限ID
     * @param roleName 权限名称
     * @return IResult
     */
    @PatchMapping("/role")
    public IResult<String> updateRole(Long roleId, String roleName) {
        if (null == roleId) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "roleId");
        }
        if (StringUtils.isBlank(roleName)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "roleName");
        }
        return authService.updateRole(roleId, roleName);
    }
}
