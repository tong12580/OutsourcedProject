package com.business.servelt;

//import com.business.service.interfaces.users.UserInfoService;
//import org.springframework.beans.factory.annotation.Autowired;

import com.business.common.context.SpringContextUtil;
import com.business.thread.TaskExecutorConfig;
import com.business.thread.task.AsyncTaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by yuton on 2016/9/14.
 */
@WebServlet(name = "init", loadOnStartup = 1, description = "缓存初始化加载")
public class CacheInitializerServlet extends HttpServlet {

//    @Autowired
//    private UserInfoService userInfoService;

    @Override
    public void init() throws ServletException {
        super.init();
        /** 1缓存初始化加载*/
        //        userInfoService.fineOne("樱桃");

        /** 2测试多线程*/
//        AsyncTaskService asyncTaskService = SpringContextUtil.getBean(AsyncTaskService.class);
//        for (int i = 0; i < 10; i++) {
//            asyncTaskService.executeAsyncTask(i);
//            asyncTaskService.executeAsyncTaskPlus(i);
//        }

    }
}
