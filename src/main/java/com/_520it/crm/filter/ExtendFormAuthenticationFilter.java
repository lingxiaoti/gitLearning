package com._520it.crm.filter;

import com._520it.crm.domain.SystemMenu;
import com._520it.crm.service.ISystemMenuService;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by 华硕 on 2017/9/28.
 */
public class ExtendFormAuthenticationFilter extends FormAuthenticationFilter {
    @Setter
    private ISystemMenuService systemMenuService;
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //直接响应登录成功的JSON
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        //登录成功之后,把用户权限放入session中
        List<SystemMenu> indexMenu = systemMenuService.indexMenu();
        SecurityUtils.getSubject().getSession().setAttribute("USER_MENU_IN_SESSION",indexMenu);
        //___________________________________

        System.out.println("登陆成功");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("{\"success\":true,\"msg\":\"登入成功\"}");
        out.flush();
        out.close();
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //直接响应登录失败的json数据
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"密码错误\"}");
            } else if ("UnknownAccountException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"账号不存在\"}");
            } else if ("LockedAccountException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"账号被锁定\"}");
            }else if("AuthenticationException".equals(message)){
                out.println("{\"success\":false,\"msg\":\"账号被锁定\"}");
            }else {
                out.println("{\"success\":false,\"msg\":\"未知错误\"}");
            }
            out.flush();
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}
