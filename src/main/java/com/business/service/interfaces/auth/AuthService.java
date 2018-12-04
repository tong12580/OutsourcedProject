package com.business.service.interfaces.auth;

import com.business.pojo.dto.user.UserDTO;

import com.jokers.common.response.IResult;
import org.springframework.data.domain.Page;

/**
 * 权限服务
 *
 * @author yutong
 * @version 1.0
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


    /**
     * 分页查询所有用户权限
     *
     * @param pageNum  第几页
     * @param pageSize 页面记录数
     */
    IResult<Page<UserDTO>> queryUsers(Integer pageNum, Integer pageSize);

    /**
     * 修改用户权限
     *
     * @param userId  用户ID
     * @param newRole 新权限名称
     * @return String
     */
    IResult<String> updateUserRole(Long userId, String newRole);
}
