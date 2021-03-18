package com.wzr.dao;

import com.wzr.model.Po.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentMapper
{
    /**
     *  ********************* 查 query *********************
     */
    //获取文章列表
    List<Object> getArtList(int user_id, int start, int page, String keyword, int dir_id);

    //根据id查询文章
    Article queryArtById(int art_id, int user_id);

    /**
     *  ********************* 增 insert *********************
     */
    //发布一篇文章
    int publishArticle(Article article);

    /**
     *  ********************* 删 insert *********************
     */
    //根据dir_id和user_id删除文章
    int deleteArtById(int art_id, int user_id);

    /**
     *  ********************* 改 update *********************
     */
    int updateArticle(Article article);
}
