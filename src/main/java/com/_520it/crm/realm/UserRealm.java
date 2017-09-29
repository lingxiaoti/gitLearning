package com._520it.crm.realm;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Role;
import com._520it.crm.service.IEmployeeService;
import com._520it.crm.service.IPermissionService;
import com._520it.crm.service.IRoleService;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华硕 on 2017/9/28.
 */
public class UserRealm extends AuthorizingRealm{
    @Setter
    private IEmployeeService employeeService;
    @Setter
    private IRoleService roleService;

    @Setter
    private IPermissionService permissionService;
    @Override
    public String getName() {
        return "UserRealm";
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名
        String username=(String) token.getPrincipal();
        //根据用户名查询当前用户,然后让底层自动完成认证功能
        Employee current=employeeService.queryByName(username);
        if(current == null){
            return null;
        }
        //这句话的意思是:从数据库中查询出来的用户和密码和传进来的用户和密码进行匹配
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(current,current.getPassword(),this.getName());
        return info;
    }

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户
        Employee current = (Employee) principals.getPrimaryPrincipal();

        List<String> roles = null;
        List<String> perms=null;

        if(current.isAdmin()){//如果是管理员,拥有全部的角色和权限
            List<Role> roleList = roleService.selectAll();
            roles=new ArrayList<>();
            for(Role role :roleList){
                roles.add(role.getSn());
            }
            perms=new ArrayList<>();
            perms.add("*:*");
        }else {
            //根据用户的id查询所拥有的角色和权限集合
            roles = roleService.queryRoleListByEmployeeId(current.getId());
            perms= permissionService.queryPermissionListByEmployeeId(current.getId());
        }

        //生成授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(perms);
        return info;
    }
}
