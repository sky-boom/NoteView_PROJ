package com.wzr.model.Po;

import java.sql.Timestamp;

public class Article
{
    private int art_id;
    private Timestamp create_time;
    private Timestamp update_time;
    private int user_id;
    private String title;
    private String tags;    //split(",");
    private String content;
    private int type;
    private int published;
    private int comments_num;
    private int hits;
    private int likes;
    private int dir_id;

    public Article()
    {
    }

    public Article(int art_id, Timestamp create_time, Timestamp update_time, int user_id, String title, String tags, String content, int type, byte published, int comments_num, int hits, int likes, int dir_id)
    {
        this.art_id = art_id;
        this.create_time = create_time;
        this.update_time = update_time;
        this.user_id = user_id;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.type = type;
        this.published = published;
        this.comments_num = comments_num;
        this.hits = hits;
        this.likes = likes;
        this.dir_id = dir_id;
    }

    public int getArt_id()
    {
        return art_id;
    }

    public void setArt_id(int art_id)
    {
        this.art_id = art_id;
    }

    public Timestamp getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time)
    {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time()
    {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time)
    {
        this.update_time = update_time;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getPublished()
    {
        return published;
    }

    public void setPublished(int published)
    {
        this.published = published;
    }

    public int getComments_num()
    {
        return comments_num;
    }

    public void setComments_num(int comments_num)
    {
        this.comments_num = comments_num;
    }

    public int getHits()
    {
        return hits;
    }

    public void setHits(int hits)
    {
        this.hits = hits;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getDir_id()
    {
        return dir_id;
    }

    public void setDir_id(int dir_id)
    {
        this.dir_id = dir_id;
    }

    @Override
    public String toString()
    {
        return "Article{" +
                "art_id=" + art_id +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", tags='" + tags + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", published=" + published +
                ", comments_num=" + comments_num +
                ", hits=" + hits +
                ", likes=" + likes +
                ", dir_id=" + dir_id +
                '}';
    }
}
