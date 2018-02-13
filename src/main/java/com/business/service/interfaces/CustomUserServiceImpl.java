package com.business.service.interfaces;


import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;


/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.config
 * @since 上午10:32 2017/12/25
 */
public class CustomUserServiceImpl implements UserDetailsService {

    @Resource
    private UserDTORepository sysUserDTORepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = sysUserDTORepository.findByUsername(s);
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
