package com.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author yutong
 */
@ServletComponentScan
@SpringBootApplication
@EnableCaching
@PropertySource(value = {"classpath:copyWriteUI.properties", "classpath:api.yml"})
public class OutsourcedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutsourcedProjectApplication.class, args);
    }
}
