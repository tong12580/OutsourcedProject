package com.business.service.implementation.system;

import com.business.dao.system.SystemConfigRepository;
import com.business.pojo.dto.system.SystemConfig;
import com.business.service.interfaces.system.SystemConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/2/27 23:05
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Resource
    private SystemConfigRepository systemConfigRepository;

    @Override
    public SystemConfig getSystemConfigByKey(String key) {
        return systemConfigRepository.findBySysKey(key);
    }

    @Override
    public SystemConfig save(SystemConfig systemConfig) {
        return systemConfigRepository.save(systemConfig);
    }
}
