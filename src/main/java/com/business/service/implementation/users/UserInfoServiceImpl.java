package com.business.service.implementation.users;

import com.business.dao.users.UserInfoRepository;
import com.business.pojo.dto.UserInfo;
import com.business.service.interfaces.users.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by yuton on 2016/9/15.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    @Cacheable(value = "outsourced", key = "#name")
    public UserInfo fineOne(String name) {
        UserInfo userInfo = userInfoRepository.findFirstByUserName("樱桃");
        System.out.print("缓存了key为"+name+"的鬼");
        return userInfo;
    }
}
