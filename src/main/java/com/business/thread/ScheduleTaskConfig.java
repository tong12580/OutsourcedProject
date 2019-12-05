package com.business.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by yuton on 2016/9/15.
 */
@Configuration
@ComponentScan("com.business.thread.schedule")
@EnableScheduling
public class ScheduleTaskConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }

    @Bean
    public ExecutorService taskExecutor() {
        return new ScheduledThreadPoolExecutor(2,  new ThreadFactoryBuilder()
                .setNameFormat("scheduleTaskExecutor-fixed-%d")
                .build());
    }
}
