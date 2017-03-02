package com.business.service.implementation.users;

import com.business.dao.users.UserOauthRepository;
import com.business.pojo.dto.user.UserOauthDTO;
import com.business.service.interfaces.users.UserOauthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/2/27 22:47
 */
@Service
public class UserOauthServiceImpl implements UserOauthService {
    @Resource
    private UserOauthRepository userOauthRepository;

    @Override
    public UserOauthDTO saveUserOauth(UserOauthDTO userOauthDTO) {
        return userOauthRepository.save(userOauthDTO);
    }

    @Override
    public UserOauthDTO findUserOauthByUserId(Integer userId) {
        return userOauthRepository.findByUserIdAndStatusTrue(userId);
    }
}
