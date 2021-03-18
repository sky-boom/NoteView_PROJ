package com.wzr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "提供具体视图的相关接口")  //标记当前Contrller的功能
@Controller
public class ViewController     //模板引擎，只提供视图接口，(理解前后端分离)
{
    @ApiOperation("返回index.html")   //标记方法功能
    @RequestMapping(value = {"/", "/index"})
    public String index()
    {
        return "index";
    }

    @RequestMapping("/home")
    public String home()
    {
        return "home";
    }

    @RequestMapping("/myblog")
    public String myblog()
    {
        return "myblog";
    }

    @RequestMapping("/read")
    public String read()
    {
        return "read";
    }

    @RequestMapping("/dirview")
    public String dirview()
    {
        return "dirview";
    }

    @RequestMapping("/markdown")
    public String markdown(Model model)
    {
        model.addAttribute("idEdit", 0);    //默认值
        model.addAttribute("art_id", 1);
        return "markdown";
    }

    @RequestMapping("/schedule")
    public String schedule()
    {
        return "admin/schedule";
    }

}
