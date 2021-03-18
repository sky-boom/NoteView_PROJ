package com.wzr.config.shiro;

import com.wzr.model.Po.User_auth;
import com.wzr.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

// 自定义的UserRealm，继承AuthorizingRealm
public class UserRealm extends AuthorizingRealm
{
    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        System.out.println("执行了授权==========>");

//        String identity = (String) SecurityUtils.getSubject().getPrincipal();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //获得该用户角色
//        String role = userService.queryRoleByName(identity);
//        System.out.println("授权角色:" + role);
//        Set<String> set = new HashSet<>();
//        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
//        set.add(role);
//        //设置该用户拥有的角色
//        info.setRoles(set);
//        return info;
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
    {
        System.out.println("执行了认证==========>");
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //连接真实数据库
        User_auth user = userService.queryByName(userToken.getUsername());   //调用service层方法
        if(user == null)    //没有这个用户
        {
            System.out.println("没有这个用户");
            return null;    //UnknownAccountException
        }
        //密码，shiro自动验证，自动加密了
        return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), "");
    }
}
