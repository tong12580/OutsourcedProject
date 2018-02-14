package com.business.controller.auth;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yutong
 * @version 1.0
 * @description 权限控制器
 * @since 2018/2/14 20:35
 */
@RestController
@RequestMapping("/admin")
public class AuthCtrl {
    /**
     * 添加新权限名称
     *
     * @param roleName 权限名称
     * @return IResult
     */
    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public IResult<String> addRole(String roleName) {
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS);
    }

    /**
     * 查询权限表
     *
     * @return IResult
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public IResult queryRoles() {
        return null;
    }

    /**
     * 修改权限名
     *
     * @return IResult
     */
    @RequestMapping(value = "/role", method = RequestMethod.PATCH)
    public IResult<String> updateRole(Long roleId, String roleName) {
        return null;
    }
}
