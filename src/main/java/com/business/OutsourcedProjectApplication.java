package com.business;

import com.business.common.message.CopyWriteUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@ServletComponentScan
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(CopyWriteUI.class)
public class OutsourcedProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutsourcedProjectApplication.class, args);
	}
}
