package com.business.common.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuton
 * @version 1.0
 * @description 页面显示错误信息
 * @since 2017/2/7 16:54
 */
@Component
@ConfigurationProperties(prefix = "ui", locations = "classpath:copyWriteUI.properties")
public class CopyWriteUI {

    @Getter
    @Setter
    private String phoneException; //手机号错误
    @Getter
    @Setter
    private String passwordException; // 密码错误
    @Getter
    @Setter
    private String havePhone; //手机号已注册

}
