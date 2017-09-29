package com._520it.crm.web.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by 华硕 on 2017/9/28.
 */
@ControllerAdvice//控制器的增强
public class ExceptionHanlder {

    @ExceptionHandler(UnauthorizedException.class)//当控制器有异常的时候,就执行下面的方法
    public void handlerException(HandlerMethod handlerMethod, HttpServletResponse response){//封装了所有控制器的方法
        //根据方法判断控制器方法上是否有@ResponseBody这个注解,如果有就说明是ajax请求,没有,就说明是页面请求
        try {
            if(handlerMethod.getMethodAnnotation(ResponseBody.class)!=null){
                //返回到前台的json数据{"success":false,"msg":"你没有权限","total":0,"rows":[]}
                response.setCharacterEncoding("utf-8");
                response.getWriter().write("{\"success\":false,\"msg\":\"你没有权限\",\"total\":0,\"rows\":[]}");
            }else {
                //否则重定向nopermission.jsp
                response.sendRedirect("/nopermission.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
