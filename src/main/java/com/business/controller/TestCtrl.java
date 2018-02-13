package com.business.controller;

import com.business.pojo.dto.user.Msg;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.ctrl
 * @since 上午10:57 2017/12/25
 */
@Controller
public class TestCtrl {

    @RequestMapping("/")
    public String index(Model model) {
        Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", msg);
        return "index";
    }

    @ResponseBody
    @RequestMapping("test")
    public String test(String x) {
        return x;
    }
}
