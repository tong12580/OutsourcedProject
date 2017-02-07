package com.business.common.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yuton
 * @version 1.0
 * @description 页面显示错误信息
 * @since 2017/2/7 16:54
 */
@ConfigurationProperties(prefix = "ui", locations = "classpath:copyWriteUI.properties")
public class CopyWriteUI {

    @Getter
    @Setter
    private String phoneException;
    @Getter
    @Setter
    private String passwordException;
    @Getter
    @Setter
    private String havePhone;

}
