package com.business.dao.users;

import com.business.pojo.dto.user.UserOauthDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author yuton
 * @version 1.0
 * @description token服务
 * @since 2017/2/8 12:15
 */
public interface UserOauthRepository extends JpaRepository<UserOauthDTO, Integer> {

    UserOauthDTO findByUserIdAndStatusTrue(Integer userId);

    UserOauthDTO findByAccessTokenAndStatusTrue(String accessToken);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserOauthDTO u " +
            "SET u.accessToken = :accessToken, u.expiresIn = :expiresIn, u.updateTime = :updateTime " +
            "WHERE u.userId = :userId")
    int updateByUserId(@Param("accessToken") String accessToken,
               @Param("userId") Integer userId,
               @Param("expiresIn") Long expiresIn,
               @Param("updateTime")Date updateTime);

    UserOauthDTO findOneOauthByUserIdJoinUser(Integer userId);
}

