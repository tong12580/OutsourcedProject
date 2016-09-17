package com.business.filter.httpEncodingFilter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;

/**
 * Created by yuton on 2016/9/2.
 */
@Data
@ConfigurationProperties(prefix = "context.http.encoding")
public class HttpEncodingProperties {
    private boolean force = true;
    private Charset charset = Charset.forName("UTF-8");
}
