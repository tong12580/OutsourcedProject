package com.business.common.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuton
 * @version 1.0
 * @description 页面显示错误信息
 * @since 2017/2/7 16:54
 */
@Component
@ConfigurationProperties(prefix = "ui")
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
    @Getter
    @Setter
    private String loginError; //用户名密码错误

    @Getter
    @Setter
    private String issuer;//颁发者

    @Getter
    @Setter
    private String secret;//加密密钥

    @Setter
    @Getter
    private String tokenHead;

    @Setter
    @Getter
    private String tokenHeader;

    @Getter
    @Setter
    private String basicHead;
}
