package com.business.thread;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by yuton on 2016/9/15.
 */
@Configuration
@ComponentScan("com.business.thread.schedule")
@EnableScheduling
public class ScheduleTaskConfig {
}
