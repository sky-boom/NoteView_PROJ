package com.wzr.model.Po;
/**
 *  文件夹目录树
 */
public class Dir_tree
{
    private int id;
    private int ancestor_id;
    private int level;
    private int dir_id;

    public Dir_tree()
    {
    }

    public Dir_tree(int id, int ancestor_id, int level, int dir_id)
    {
        this.id = id;
        this.ancestor_id = ancestor_id;
        this.level = level;
        this.dir_id = dir_id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAncestor_id()
    {
        return ancestor_id;
    }

    public void setAncestor_id(int ancestor_id)
    {
        this.ancestor_id = ancestor_id;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
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
        return "Dir_tree{" +
                "id=" + id +
                ", ancestor_id=" + ancestor_id +
                ", level=" + level +
                ", dir_id=" + dir_id +
                '}';
    }
}
