package com.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.ctrl
 * @since 下午4:07 2017/12/25
 */
@Controller
@RequestMapping("/xxx")
public class TestCtrl2 {
    
    @RequestMapping("/xcl")
    public String xcl() {
        return "xcl";
    }
}
