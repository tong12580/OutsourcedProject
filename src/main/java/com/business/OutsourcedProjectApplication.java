package com.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yutong
 */
@ServletComponentScan
@SpringBootApplication
@EnableCaching
@PropertySource(value = {"classpath:copyWriteUI.properties"})
public class OutsourcedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutsourcedProjectApplication.class, args);
    }
}
