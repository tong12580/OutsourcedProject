package com.business.service.interfaces.users;

import com.business.pojo.dto.user.UserOauthDTO;

/**
 * @author yutong
 * @version 1.0
 * @description 用户token服务
 * @since 2017/2/27 22:42
 */
public interface UserOauthService {

    /**
     * @description 保存用户token
     * @param userOauthDTO {@link UserOauthDTO}
     * @return {@link UserOauthDTO}
     */
    UserOauthDTO saveUserOauth(UserOauthDTO userOauthDTO);

    /**
     * @description 根据用户id查询token
     * @param userId {@link Integer}
     * @return {@link UserOauthDTO}
     */
    UserOauthDTO findUserOauthByUserId(Integer userId);
}
