package com.business.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuton on 2016/9/15.
 */
@Configuration
@EnableAsync
@ComponentScan
public class TaskExecutorConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadFactoryBuilder()
                        .setNameFormat("taskExecutor-fixed-%d")
                        .build());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
