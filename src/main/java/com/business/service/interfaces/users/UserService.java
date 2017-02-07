package com.business.service.interfaces.users;

import com.business.pojo.dto.user.UserDTO;

/**
 * @author yuton
 * @version 1.0
 * @description 用户表服务
 * @since 2017/1/23 18:02
 */
public interface UserService {

    /**
     * @param userId
     * @return
     * @description 根据用户id查询用户
     */
    UserDTO getUserById(Integer userId);

    /**
     * @param phone
     * @return
     * @description 根据手机号查询用户
     */
    UserDTO getUserByPhone(String phone);

    /**
     * @param openid
     * @return
     * @description 根据openid查询用户
     */
    UserDTO getUserByOpenid(String openid);

}
