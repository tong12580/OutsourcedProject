package com.business.dao.users;

import com.business.pojo.dto.user.UserAddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/1/30 21:25
 */
public interface UserAddressRepository extends JpaRepository<UserAddressDTO, Integer> {
}
