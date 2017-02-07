package com.business.dao.users;

import com.business.pojo.dto.user.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/1/23 17:21
 */
public interface UserRepository extends JpaRepository<UserDTO, Integer> {
    UserDTO findByPhoneAndStatusTrue(String phone);

    UserDTO findByOpenidAndStatusTrue(String openid);
}
