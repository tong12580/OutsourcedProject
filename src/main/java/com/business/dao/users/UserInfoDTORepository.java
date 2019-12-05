package com.business.dao.users;

import com.business.pojo.dto.user.UserInfoDTO;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @since 2018/2/14 18:34
 */
public interface UserInfoDTORepository extends JpaRepository<UserInfoDTO, Long> {
    UserInfoDTO findByUserId(Long userId);
}
