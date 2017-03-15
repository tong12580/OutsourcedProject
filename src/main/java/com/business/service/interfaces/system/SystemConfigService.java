package com.business.service.interfaces.system;

import com.business.pojo.dto.system.SystemConfig;

/**
 * @author yutong
 * @version 1.0
 * @description 系统配置服务
 * @since 2017/2/25 14:37
 */
public interface SystemConfigService {
    /**
     * @description 根据key获取参数设置
     * @param key {@link String}
     * @return {@link SystemConfig}
     */
    SystemConfig getSystemConfigByKey(String key);

    /**
     * @description 保存系统参数设置
     * @param systemConfig {@link SystemConfig}
     * @return {@link SystemConfig}
     */
    SystemConfig save(SystemConfig systemConfig);
}
