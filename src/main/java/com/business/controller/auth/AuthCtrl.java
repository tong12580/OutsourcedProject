package com.business.controller.auth;

import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.auth.AuthService;
import com.jokers.common.message.ResultMessage;
import com.jokers.common.response.IResult;
import com.jokers.common.response.IResultUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * 权限控制器
 * @since 2018/2/14 20:35
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Api(value = "权限控制器", tags = {"权限管理"},
        authorizations = {@Authorization(value = "basicAuth"), @Authorization(value = "token")})
public class AuthCtrl {
    @Resource
    private AuthService authService;

    @PutMapping("/role")
    @ApiOperation(value = "添加新权限名称", notes = "添加新权限名称")
    @ApiImplicitParam(name = "roleName", value = "权限名称", dataType = "String", required = true)
    public IResult<String> addRole(String roleName) {
        if (StringUtils.isEmpty(roleName)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "roleName");
        }
        return authService.addRole(roleName);
    }

    @GetMapping("/roles")
    @ApiOperation(value = "查询权限表", notes = "查询权限表")
    public IResult queryRoles() {
        return authService.queryRoles();
    }


    @PatchMapping("/role")
    @ApiOperation(value = "修改权限名", notes = "修改权限名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "权限名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "roleId", value = "权限ID", dataType = "Long", required = true)
    })
    public IResult<String> updateRole(Long roleId, String roleName) {
        if (null == roleId) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "roleId");
        }
        if (StringUtils.isBlank(roleName)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "roleName");
        }
        return authService.updateRole(roleId, roleName);
    }

    @GetMapping("/users")
    @ApiOperation(value = "查询所有用户权限信息", notes = "分页查询所有用户权限信息，默认从第0页开始")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面记录数", dataType = "int")
    })
    public IResult<Page<UserDTO>> queryUsers(Integer pageNum, Integer pageSize) {
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = 10;
        }
        return authService.queryUsers(pageNum, pageSize);
    }
}
