package com.wzr.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzr.config.redis.ListendHandler;
import com.wzr.model.Po.Article;
import com.wzr.model.Vo.ArticleInfo;
import com.wzr.service.ContentService;
import com.wzr.service.UserService;
import com.wzr.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ContentController
{
    @Autowired
    ContentService contentService;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    //发布文章
    @RequestMapping("/content/publish")
    @ResponseBody
    public int publishArticle(@RequestParam("title") String title,
                              @RequestParam("tags") String tags,
                              @RequestParam("content") String content,
                              @RequestParam("type") int type,
                              @RequestParam(value = "dir_id", defaultValue = "-1", required = false) int dir_id,
                              @RequestParam("isEdit") boolean isEdit,
                              @RequestParam(value = "art_id", defaultValue = "0", required = false) int art_id)
    {
//        System.out.printf("%s, %s, %s, %d, %d", title, tags, content, type, dir_id);
        Article article = new Article();
        article.setTitle(title);
        article.setTags(tags);
        article.setContent(content);
        article.setType(type);
        //获取用户id
        String identity = (String) SecurityUtils.getSubject().getPrincipal();
        article.setUser_id(userService.queryIdByName(identity));
        //如果是编辑模式，添加文章id. 只支持修改：title, tags, content
        if(isEdit)
            article.setArt_id(art_id);

        return contentService.publishArticle(article, dir_id, isEdit);
    }

    //查看我的文章列表
    @RequestMapping("/content/mylist")
    @ResponseBody
    public List<Object> getArtList( @RequestParam(required = false, defaultValue = "0") int start,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "") String keyword,
                                    @RequestParam(required = false, defaultValue = "0") int dir_id)
    {
        int user_id = userService.queryIdByName((String) SecurityUtils.getSubject().getPrincipal());
        // start: 开始分页页数，初始为0
        // page: 每页个数, 约定为10
        System.out.println("start=" + start + ", page=" + page);
        return contentService.getArtList(user_id, start, page, keyword, dir_id);
    }

    //进入指定文章id视图
    @RequestMapping("/content/blog/{art_id}")
    public String read(@PathVariable int art_id, Model model)
    {
        String identity = (String) SecurityUtils.getSubject().getPrincipal();
        ArticleInfo articleInfo = contentService.getArtView(art_id, userService.queryIdByName(identity));
        articleInfo.setAuthorName(identity);

        model.addAttribute("artInfo", articleInfo);

        //指定文章id浏览量+1
        redisUtil.incr(String.valueOf(art_id), "hits", 1);

        return "read";
    }

    //ajax请求从redis中返回点击量
    @RequestMapping("/content/hit/{art_id}")
    @ResponseBody
    public int hit(@PathVariable int art_id)
    {
        double ans = (double) redisUtil.get(String.valueOf(art_id), "hits");
        int hits = (int) ans;
        System.out.print("文章" + art_id + "的点击量:" + hits + ", ");
        return hits;
    }


    //
    @RequestMapping("/content/edit/{art_id}")
    public String edit(@PathVariable int art_id, Model model)
    {
        //获取已有的文章信息
        String identity = (String) SecurityUtils.getSubject().getPrincipal();
        ArticleInfo articleInfo = contentService.getArtView(art_id, userService.queryIdByName(identity));
        //返回到markdown视图
        model.addAttribute("artInfo", articleInfo);
        model.addAttribute("isEdit", 1);
        model.addAttribute("art_id", art_id);

        return "markdown";
    }

    //根据文章id删除文章
    @RequestMapping("/content/del/{art_id}")
    @ResponseBody
    public void deleteArtById(@PathVariable int art_id)
    {
        String identity = (String) SecurityUtils.getSubject().getPrincipal();
        contentService.deleteArtById(art_id, userService.queryIdByName(identity));
    }

    //markdown上传图片
    @RequestMapping("/img/upload")
    @ResponseBody
    public JSONObject test(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file) throws UnsupportedEncodingException
    {
        /*
         *  获取项目classes/static的地址
         *  处理前:  E:\IntelliJ%20IDEA%202020.2.2\workspace\NoteView_PROJ\target\classes\static\imageUpload
         *  处理后： E:/IntelliJ IDEA 2020.2.2/workspace/NoteView_PROJ/target/classes/static/imageUpload
         */

        String temp_path = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + "/imageUpload";
        String targetPath = URLDecoder.decode(temp_path, "utf-8");
        String fileName = file.getOriginalFilename();   //file_1612749164000.png
        System.out.println(targetPath);
        //创建文件
        File targetFile = new File(targetPath, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        JSONObject res = new JSONObject();
        //将文件保存指定目录
        try {
            file.transferTo(targetFile);
        }
        catch (Exception e) {
            System.out.println("文件保存异常!\n" + e);
            return res;
        }
        res.put("url", "/image/" + fileName);
        res.put("success", 1);
        res.put("message", "上传成功!");
        System.out.println(res);

        return res;
    }
}
