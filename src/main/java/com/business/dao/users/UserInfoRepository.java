package com.business.dao.users;

import com.business.pojo.dto.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yuton on 2016/9/13.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    UserInfo findFirstByUserName(String name);
}
