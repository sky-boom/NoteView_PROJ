package com.wzr.controller;

import com.wzr.config.redis.ListendHandler;
import com.wzr.model.Po.Article;
import com.wzr.service.ContentService;
import com.wzr.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController           //当下全部返回指定数据，无需@ResponseBody
public class LoginController
{
    @Autowired
    private ContentService contentService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/tologin")
    public boolean tologin(String account, String password, Model model)
    {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);

        //这个方法会把用户信息提交到 doGetAuthtication【认证】方法中校验用户信息。
        try
        {
            subject.login(token);   //执行登录的方法，如果没有异常，就成功
            return true;  //登录成功，交给ajax完成进入首页
        }
        catch (Exception e)   //账户或密码错误
        {
            return false;
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/index";
    }

    @RequestMapping("/toredis")
    public void toRedis()
    {
        List<Article> artList = contentService.getAllArt();
        artList.forEach(art -> {
            double ans = (double) redisUtil.get(String.valueOf(art.getArt_id()), "hits");
            art.setHits((int) ans);
            contentService.updateArticle(art);
        });
        System.out.println("数据已从redis中赋值到MySQL");
    }

    @GetMapping(value = "/test")
    public String test()
    {
        return "test_str";
    }
}
