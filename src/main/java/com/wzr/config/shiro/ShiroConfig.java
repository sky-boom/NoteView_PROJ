package com.wzr.config.shiro;

import com.wzr.config.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig
{
    // 步骤【3】：配置「ShiroFilterFactoryBean」
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager)
    {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        // 添加shiro的内置过滤器
        /*
            anon: 所有用户都能访问的
            authc: 没有登录？拦截！
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/assets/**", "anon");    //css、js、imgs
        filterMap.put("/layui/**", "anon");
        filterMap.put("/markdown/**", "anon");
        filterMap.put("/", "anon");         // 首页
        filterMap.put("/index", "anon");    // 首页
        filterMap.put("/tologin", "anon");    // 登录页
        filterMap.put("/logout","logout");  //触发注销机制

//        filterMap.put("/**", "authc");  //终极拦截, 放到最后
        bean.setFilterChainDefinitionMap(filterMap);

//        // 拦截后的跳转界面
//        bean.setLoginUrl("/tip/notLogin");
//        // 成功后的跳转界面
//        bean.setSuccessUrl("/main");
//        // 没有权限的跳转界面
//        bean.setUnauthorizedUrl("/tip/notRole");

        return bean;
    }

    // 步骤【2】：配置「defaultWebSecurityManager」
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm)  //spring自动装配bean，名称默认开头小写
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 步骤【1】：创建「realm」对象
    @Bean
    public UserRealm userRealm()
    {
        return new UserRealm();
    }

    // 整合Thymeleaf+shiro
//    @Bean
//    public ShiroDialect getShiroDialect()
//    {
//        return new ShiroDialect();
//    }
}
