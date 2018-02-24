package com.business.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuton
 * SpringContextUtil
 * <p>获取spring容器，以访问容器中定义的其他bean</p>
 * @since May 6, 2011 2:35:22 PM
 */
@Configuration
public class ApplicationContextConfig implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private ApplicationContextConfig() {
    }

    /**
     * 获取对象
     *
     * @return Object
     */
    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }

    public static <T> T getBean(Class<T> tClass) throws BeansException {
        return applicationContext.getBean(tClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if (null == ApplicationContextConfig.applicationContext)
            ApplicationContextConfig.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}

