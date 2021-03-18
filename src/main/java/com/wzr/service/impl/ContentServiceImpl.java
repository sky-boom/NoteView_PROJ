package com.wzr.service.impl;

import com.wzr.dao.ContentMapper;
import com.wzr.dao.DirectoryMapper;
import com.wzr.model.Po.Article;
import com.wzr.model.Vo.ArticleInfo;
import com.wzr.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService
{
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    DirectoryMapper directoryMapper;

    @Override
    public int publishArticle(Article article, int dir_id, boolean isEdit)
    {
        //如果只是修改文章
        if(isEdit)
        {
            contentMapper.updateArticle(article);
            return article.getArt_id();
        }
        //初始化各参数
        article.setPublished(1);
        article.setComments_num(0);
        article.setHits(0);
        article.setLikes(0);
        article.setDir_id(dir_id);
        contentMapper.publishArticle(article);

        return article.getArt_id();   //id已赋值
    }

    @Override
    public List<Object> getArtList(int user_id, int start, int page, String keyword, int dir_id)
    {
        return contentMapper.getArtList(user_id, start, page, keyword, dir_id);
    }

    @Override
    public ArticleInfo getArtView(int art_id, int user_id)
    {
        Article article = contentMapper.queryArtById(art_id, user_id);
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setTitle(article.getTitle());
        articleInfo.setHits(article.getHits());
        //获取年月日
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(article.getCreate_time()).toString().split("-");
        articleInfo.setCr_year(Integer.parseInt(strNow[0]));			//获取年
        articleInfo.setCr_month(Integer.parseInt(strNow[1]));			//获取月
        articleInfo.setCr_day(Integer.parseInt(strNow[2]));			//获取日
        articleInfo.setUpdateTime(article.getUpdate_time());
        articleInfo.setContent(article.getContent());
        articleInfo.setLikes(article.getLikes());
        articleInfo.setComments_num(article.getComments_num());

        return articleInfo;
    }

    @Override
    public void deleteArtById(int art_id, int user_id)
    {
        contentMapper.deleteArtById(art_id, user_id);
    }
}
