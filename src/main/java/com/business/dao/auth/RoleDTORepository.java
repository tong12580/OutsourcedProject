package com.business.dao.auth;

import com.business.pojo.dto.user.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/2/14 18:57
 */
public interface RoleDTORepository extends JpaRepository<Role, Long> {
}
