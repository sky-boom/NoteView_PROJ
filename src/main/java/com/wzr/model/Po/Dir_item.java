package com.wzr.model.Po;

/**
 *  单个文件夹
 */
public class Dir_item
{
    private int dir_id;
    private String dir_name;
    private int parent_dir_id;
    private int user_id;

    public Dir_item()
    {
    }

    public Dir_item(int dir_id, String dir_name, int parent_dir_id, int user_id)
    {
        this.dir_id = dir_id;
        this.dir_name = dir_name;
        this.parent_dir_id = parent_dir_id;
        this.user_id = user_id;
    }

    public int getDir_id()
    {
        return dir_id;
    }

    public void setDir_id(int dir_id)
    {
        this.dir_id = dir_id;
    }

    public String getDir_name()
    {
        return dir_name;
    }

    public void setDir_name(String dir_name)
    {
        this.dir_name = dir_name;
    }

    public int getParent_dir_id()
    {
        return parent_dir_id;
    }

    public void setParent_dir_id(int parent_dir_id)
    {
        this.parent_dir_id = parent_dir_id;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "Dir_item{" +
                "dir_id=" + dir_id +
                ", dir_name='" + dir_name + '\'' +
                ", parent_dir_id=" + parent_dir_id +
                ", user_id=" + user_id +
                '}';
    }
}
