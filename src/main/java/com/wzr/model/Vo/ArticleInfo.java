package com.wzr.model.Vo;

import java.sql.Timestamp;

/**
 *  具体文章页面
 */
public class ArticleInfo
{
    private String title;           //文章标题
    private String authorName;      //文章作者
    private int hits;               //点击量(围观群众)
    private int cr_year;            //发布时间
    private int cr_month;
    private int cr_day;
    private Timestamp updateTime;   //更新时间
    private String content;         //文章内容
    private int likes;              //点赞量
    private int comments_num;       //评论数

    public ArticleInfo()
    {
    }

    public ArticleInfo(String title, String authorName, int hits, int cr_year, int cr_month, int cr_day, Timestamp updateTime, String content, int likes, int comments_num)
    {
        this.title = title;
        this.authorName = authorName;
        this.hits = hits;
        this.cr_year = cr_year;
        this.cr_month = cr_month;
        this.cr_day = cr_day;
        this.updateTime = updateTime;
        this.content = content;
        this.likes = likes;
        this.comments_num = comments_num;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public int getHits()
    {
        return hits;
    }

    public void setHits(int hits)
    {
        this.hits = hits;
    }

    public int getCr_year()
    {
        return cr_year;
    }

    public void setCr_year(int cr_year)
    {
        this.cr_year = cr_year;
    }

    public int getCr_month()
    {
        return cr_month;
    }

    public void setCr_month(int cr_month)
    {
        this.cr_month = cr_month;
    }

    public int getCr_day()
    {
        return cr_day;
    }

    public void setCr_day(int cr_day)
    {
        this.cr_day = cr_day;
    }

    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getComments_num()
    {
        return comments_num;
    }

    public void setComments_num(int comments_num)
    {
        this.comments_num = comments_num;
    }

    @Override
    public String toString()
    {
        return "ArticleInfo{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", hits=" + hits +
                ", cr_year=" + cr_year +
                ", cr_month=" + cr_month +
                ", cr_day=" + cr_day +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", comments_num=" + comments_num +
                '}';
    }
}
