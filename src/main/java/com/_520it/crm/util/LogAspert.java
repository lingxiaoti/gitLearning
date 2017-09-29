package com._520it.crm.util;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.SystemLog;
import com._520it.crm.service.impl.ISystemLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by 华硕 on 2017/9/29.
 */
public class LogAspert {
    @Setter
    private ISystemLogService systemLogService;
    public void write(JoinPoint jp) throws Exception{
        System.out.println("输出系统日志.................");
        //向数据库保存日志
        SystemLog log=new SystemLog();
        log.setOpTime(new Date());
        Employee current =(Employee) SecurityUtils.getSubject().getPrincipal();
        log.setOpUser(current);
        //保存请求ip地址
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        log.setOpIp(request.getRemoteAddr());
        //保存请求的目标方法
        String function = jp.getTarget().getClass().getName()+":"+jp.getSignature().getName();
        log.setFunction(function);

        ObjectMapper mapper=new ObjectMapper();
        log.setParams(mapper.writeValueAsString(jp.getArgs()));
        systemLogService.insert(log);
    }
}
