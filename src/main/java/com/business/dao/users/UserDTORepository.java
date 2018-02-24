package com.business.dao.users;

import com.business.pojo.dto.user.UserDTO;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.dao
 * @since 上午10:31 2017/12/25
 */
public interface UserDTORepository extends JpaRepository<UserDTO, Long> {
    UserDTO findByUsername(String userName);
}
