package com.business.dao.users;

import com.business.pojo.dto.user.UserOauthDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/2/8 12:15
 */
public interface UserOauthRepository extends JpaRepository<UserOauthDTO, Integer> {
}
