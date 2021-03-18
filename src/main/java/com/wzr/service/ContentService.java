package com.wzr.service;

import com.wzr.model.Po.Article;
import com.wzr.model.Vo.ArticleInfo;

import java.util.List;

public interface ContentService
{
    //发布文章(可选择将文章发布到指定目录), 返回文章id
    int publishArticle(Article article, int dir_id, boolean isEdit);

    //获取文章列表, 第一个是列表，第二个是count
    List<Object> getArtList(int user_id, int start, int page, String keyword, int dir_id);

    //得到一篇文章所需数据
    ArticleInfo getArtView(int art_id, int user_id);

    //根据id删除文章
    void deleteArtById(int art_id, int user_id);
}
