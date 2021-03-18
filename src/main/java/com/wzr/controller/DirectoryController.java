package com.wzr.controller;

import com.wzr.model.Po.Dir_item;
import com.wzr.model.Vo.DirectoryTreeNode;
import com.wzr.model.Vo.Doc;
import com.wzr.service.DirectoryService;
import com.wzr.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  文件系统相关操作
 */
@RestController
public class DirectoryController
{
    @Autowired
    DirectoryService directoryService;
    @Autowired
    UserService userService;

    /**
     * 获取目录树
     */
    @RequestMapping(value = "/dir/getall")
    public List<DirectoryTreeNode> getTree()
    {
        String identity = (String)SecurityUtils.getSubject().getPrincipal();
        return directoryService.getDirTree(userService.queryIdByName(identity));
    }

    /**
     * 新建目录
     */
    @RequestMapping(value = "/dir/add")
    public Dir_item addDir(@RequestParam("dir_name")String newDirName,
                           @RequestParam("parent_id")int parentId)    //前端传来dir_name、parent_dir_id
    {
        Dir_item newDirItem = new Dir_item();   //dir_id为null即可(自增id)。
        newDirItem.setDir_name(newDirName);
        newDirItem.setParent_dir_id(parentId);
        String identity = (String)SecurityUtils.getSubject().getPrincipal();
        newDirItem.setUser_id(userService.queryIdByName(identity));   //获取user_id

        return directoryService.addDir(newDirItem);
    }

    /**
     * 删除目录
     */
    @RequestMapping(value = "/dir/delete")
    public void deleteDir(@RequestParam("dir_id")int deleteId)
    {
        directoryService.deleteDir(deleteId);
    }

    /**
     *  重命名目录
     */
    @RequestMapping(value = "/dir/rename")
    public void renameDir(int dir_id, String new_name)
    {
        directoryService.renameDir(dir_id, new_name);
    }

    /**
     *  剪切板移动功能
     */
    @RequestMapping(value = "/dir/move")
    public void cutAndMove(int currId, int targetId, boolean isDir)
    {
        directoryService.cutAndMove(currId, targetId, isDir);
    }

    //************************************* 以下是File ***********************************
    /**
     *  根据dir_id和art_id修改article的父目录
     */
    @RequestMapping(value = "/dir/updatedoc")
    public void updateDocDirId(int dir_id, int art_id)  //doc = art
    {
        directoryService.updateArtDirId(dir_id, art_id);
    }

    /**
     *  根据user_id获取所有doc
     */
    @RequestMapping(value = "/dir/getdoc")
    public List<Doc> getDoc()
    {
        String identity = (String)SecurityUtils.getSubject().getPrincipal();
        int user_id = userService.queryIdByName(identity);
        return directoryService.queryAllDocByUserId(user_id);
    }

    /**
     *  根据dir_id获取所有doc
     */
    @RequestMapping(value = "/dir/doc/{dir_id}")
    public List<Doc> getFileByDirId(@PathVariable int dir_id)
    {
        return directoryService.queryDocByDirId(dir_id);
    }

    /**
     *  根据art_id隐藏doc(将其父目录设置为-1)
     */
    @RequestMapping(value = "/dir/hidedoc/{art_id}")
    public void removeFile(@PathVariable int art_id)
    {
        directoryService.hideDocById(art_id);
    }

}
