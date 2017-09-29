package com._520it.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 华硕 on 2017/9/28.
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
