package com.business.dao.system;

import com.business.pojo.dto.system.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/1/23 17:33
 */
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {
    SystemConfig findBySysKey(String key);
}
