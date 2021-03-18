package com.wzr.model.Vo;

import java.util.ArrayList;
import java.util.List;

/**
 *  目录视图
 *  一个目录(DirectoryTreeNode)有它自己的名字，同时Ta的下面可能有很多目录(DirectoryTreeNode)
 */
public class DirectoryTreeNode
{
    private int dir_id;
    private String dir_name;
    private int parent_dir_id;
    private List<DirectoryTreeNode> children;
    private List<Doc> docs;

    public DirectoryTreeNode()
    {
        this.children = new ArrayList<>();
    }

    public DirectoryTreeNode(int dir_id, String dir_name, int parent_dir_id)
    {
        this.dir_id = dir_id;
        this.dir_name = dir_name;
        this.parent_dir_id = parent_dir_id;
        this.children = new ArrayList<>();
        this.docs = new ArrayList<>();
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

    public List<DirectoryTreeNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<DirectoryTreeNode> children)
    {
        this.children = children;
    }

    public List<Doc> getDocs()
    {
        return docs;
    }

    public void setDocs(List<Doc> docs)
    {
        this.docs = docs;
    }

    @Override
    public String toString()
    {
        return "DirectoryTreeNode{" +
                "dir_id=" + dir_id +
                ", dir_name='" + dir_name + '\'' +
                ", parent_dir_id=" + parent_dir_id +
                ", children=" + children +
                ", docs=" + docs +
                '}';
    }
}
