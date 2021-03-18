package com.wzr.service;

import com.wzr.model.Po.Dir_item;
import com.wzr.model.Vo.DirectoryTreeNode;
import com.wzr.model.Vo.Doc;

import java.util.List;

/**
 *  文件系统（file-system）的相关业务
 */

public interface DirectoryService
{
    //获取目录树
    List<DirectoryTreeNode> getDirTree(int user_id);

    //新建目录
    Dir_item addDir(Dir_item dir_item);

    //删除目录
    void deleteDir(int dir_id);

    //重命名目录
    void renameDir(int dir_id, String new_name);

    //根据dir_id和art_id修改article的父目录
    void updateArtDirId(int dir_id, int art_id);

    //获取某个目录id下所有file
    List<Doc> queryDocByDirId(int dir_id);

    //获取某个用户所有doc
    List<Doc> queryAllDocByUserId(int user_id);

    //移动目录或file
    void cutAndMove(int currId, int targetId, boolean isDir);

    //隐藏文章doc
    void hideDocById(int art_id);
}
