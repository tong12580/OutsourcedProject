package com.business.service.implementation.users;

import com.business.dao.users.UserRepository;
import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.users.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/1/23 18:06
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(Integer userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public UserDTO getUserByPhone(String phone) {
        return userRepository.findByPhoneAndStatusTrue(phone);
    }

    @Override
    public UserDTO getUserByOpenid(String openid) {
        return userRepository.findByOpenidAndStatusTrue(openid);
    }

    @Override
    public UserDTO getUserByNickName(String nickName) {
        return userRepository.findByNickNameAndStatusTrue(nickName);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return userRepository.save(userDTO);
    }

}
