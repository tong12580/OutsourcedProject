package com.business.thread.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuton on 2016/9/15.
 */
@Service
public class ScheduleTaskService {
    private  static final SimpleDateFormat deteFormat = new SimpleDateFormat();

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        System.out.print("每五秒执行一次"+ deteFormat.format(new Date()));
//    }
//
//    @Scheduled(cron = "0 00 16 ? * *")
//    public void fixTimeExecution() {
//        System.out.print("每天下午4点执行"+ deteFormat.format(new Date()));
//    }
}
