package com.business.service.interfaces.users;

import com.business.pojo.dto.user.UserInfo;

/**
 * Created by yuton on 2016/9/15.
 */
@FunctionalInterface
public interface UserInfoService {
    UserInfo fineOne(String name);
}
