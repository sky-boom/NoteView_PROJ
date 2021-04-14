package com.wzr.config.redis;

import com.wzr.model.Po.Article;
import com.wzr.service.ContentService;
import com.wzr.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListendHandler {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ContentService contentService;

    public ListendHandler(){
        System.out.println("开始初始化");
    }
    @PostConstruct
    public void init() {
        System.out.println("数据库开始初始化");
        //将数据中的数据一次写入Redis缓存
        List<Article> artList = contentService.getAllArt();
        System.out.println("==============" + artList);
        artList.forEach(art -> {
            //将数据库中的数据写入Redis
            redisUtil.set(String.valueOf(art.getArt_id()),"hits", art.getHits());
        });
        System.out.println("已写入Redis");
    }
    //关闭时操作
    @PreDestroy
    public void destroy(){
        System.out.println("开始关闭");
        List<Article> artList = contentService.getAllArt();
        artList.forEach(art -> {
            art.setHits((int)redisUtil.get(String.valueOf(art.getArt_id()), "hits"));
            contentService.publishArticle(art, art.getDir_id(), true);
        });
        System.out.println("数据已从redis中赋值到MySQL");
    }
}
