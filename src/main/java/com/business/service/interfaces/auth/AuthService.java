package com.business.service.interfaces.auth;

import com.business.common.response.IResult;

/**
 * @author yutong
 * @version 1.0
 * @description 权限服务
 * @since 2018/2/14 20:59
 */
public interface AuthService {


    /**
     * 查询权限表
     */
    IResult queryRoles();


    /**
     * 修改权限名
     *
     * @param roleId   权限表ID
     * @param roleName 需要修改的名称
     * @return IResult
     */
    IResult<String> updateRole(Long roleId, String roleName);

    /**
     * 添加权限
     *
     * @param roleName 添加新权限名称
     * @return IResult
     */
    IResult<String> addRole(String roleName);

}
