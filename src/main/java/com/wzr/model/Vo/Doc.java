package com.wzr.model.Vo;

import java.sql.Timestamp;

/**
 *  目录视图下的文章列表(doc)
 */
public class Doc
{
    private int art_id;
    private String title;
    private Timestamp update_time;
    private int dir_id;

    public Doc()
    {
    }

    public Doc(int art_id, String title, Timestamp update_time, int dir_id)
    {
        this.art_id = art_id;
        this.title = title;
        this.update_time = update_time;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Timestamp getUpdate_time()
    {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time)
    {
        this.update_time = update_time;
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
        return "Doc{" +
                "art_id=" + art_id +
                ", title='" + title + '\'' +
                ", update_time=" + update_time +
                ", dir_id=" + dir_id +
                '}';
    }
}
