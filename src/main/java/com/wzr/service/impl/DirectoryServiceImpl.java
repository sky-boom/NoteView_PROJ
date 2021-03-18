package com.wzr.service.impl;

import com.wzr.dao.DirectoryMapper;
import com.wzr.model.Po.Dir_item;
import com.wzr.model.Po.Dir_tree;
import com.wzr.model.Vo.DirectoryTreeNode;
import com.wzr.model.Vo.Doc;
import com.wzr.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryServiceImpl implements DirectoryService
{
    @Autowired
    DirectoryMapper directoryMapper;

    @Override
    public List<DirectoryTreeNode> getDirTree(int user_id)
    {
        List<DirectoryTreeNode> treeList = directoryMapper.getAllDirItemNodeRecord(user_id);
        List<DirectoryTreeNode> dirTree = new ArrayList<DirectoryTreeNode>();	//最终返回的目录树
        /**
         *  ------目录视图-------
         *  [1.1]
         *      [1.1.1]
         *          (title33333)    //file只允许存在于一级目录以下
         *          (title44444)
         *      [1.1.2]
         *  [1.2]
         *      [1.2.1]
         *      [1.2.2]
         *      (title55555)
         *      (title66666)
         *  --------------------
         *
         *  返回根目录视图dirTree:[ [1.1], [1.2] ]
         */
        Map<Integer, DirectoryTreeNode> map = new HashMap<>();

        //遍历每个dir_item记录，根目录的文件夹先添加到dirTree中，
        for (DirectoryTreeNode directoryTreeNode : treeList)
        {
            map.put(directoryTreeNode.getDir_id(), directoryTreeNode);
            //如果不存在父目录，说明它处在根目录
            if(directoryTreeNode.getParent_dir_id() == -1)
            {
                int currDir = directoryTreeNode.getDir_id();
                dirTree.add(map.get(currDir));
                //查询当前目录下面的doc，并添加
                List<Doc> list = directoryMapper.queryDocByDirId(currDir);
                map.get(currDir).setDocs(list);
            }
        }

        //再次遍历每个dir_item记录，构造目录树
        for (DirectoryTreeNode directoryTreeNode : treeList)
        {
            if(directoryTreeNode.getParent_dir_id() != -1)
            {
                //父节点连接子结点
                int parent = directoryTreeNode.getParent_dir_id();  //父节点
                int curr = directoryTreeNode.getDir_id();   //当前节点
                map.get(parent).getChildren().add(map.get(curr));  //父节点的儿子指向当前目录
                //父节点添加files
                List<Doc> list = directoryMapper.queryDocByDirId(curr); //获取当前目录下面的file
                map.get(curr).setDocs(list);   //连接
            }
        }

        return dirTree;
    }

    @Override
    public Dir_item addDir(Dir_item newDirItem)
    {
        Dir_tree dirTree = new Dir_tree();

        //先把dir_item插入dir_item表（dir_item有触发器，会向dir_tree自动插入自身数据）
        directoryMapper.insertOneDir(newDirItem);     //dir_item的自增dir_id已赋值
        //插入自身数据
//        dirTree.setAncestor_id(dir_item.getDir_id());
//        dirTree.setLevel(0);
//        dirTree.setDir_id();
        if (newDirItem.getParent_dir_id() == -1) {
            //如果父级ID为-1代表他是最根部的角色,则只插入自身数据
            return newDirItem;
        }
        //插入自己和[直接父目录]关联的数据
        dirTree.setAncestor_id(newDirItem.getParent_dir_id());
        dirTree.setLevel(1);
        dirTree.setDir_id(newDirItem.getDir_id());
        directoryMapper.insertOneRelation(dirTree);

        //查询[直接父目录]的所有父级目录，因为[直接父目录]已经和自己关联
        List<Dir_tree> list = directoryMapper.getAllFatherDirTree(newDirItem.getParent_dir_id());
//        System.out.println("准备查询父目录的id=" + newDirItem.getParent_dir_id());
//        System.out.println(list);
        for (Dir_tree f : list)
        {
            Dir_tree t = new Dir_tree();
            t.setDir_id(newDirItem.getDir_id());
            t.setLevel(f.getLevel() + 1);
            t.setAncestor_id(f.getAncestor_id());
            directoryMapper.insertOneRelation(t);
        }
        System.out.println(newDirItem);
        return newDirItem;
    }

    @Override
    public void deleteDir(int dir_id)
    {
        //删除所有目录,把所有file父目录设置为-1
        directoryMapper.deleteDirById(dir_id);

    }

    @Override
    public void renameDir(int dir_id, String new_name)
    {
        directoryMapper.updateDirName(dir_id, new_name);
    }

    @Override
    public void updateArtDirId(int dir_id, int art_id)
    {
        directoryMapper.updateArtDirId(dir_id, art_id);
    }

    @Override
    public List<Doc> queryDocByDirId(int dir_id)
    {
        return directoryMapper.queryDocByDirId(dir_id);
    }

    @Override
    public List<Doc> queryAllDocByUserId(int user_id)
    {
        return directoryMapper.queryAllDocByUserId(user_id);
    }

    @Override
    public void cutAndMove(int currId, int targetId, boolean isDir)
    {
        if(isDir)   //如果剪切的是目录
        {
            /*
             *  注意要删除[断开子结点]所有父联系, 还要删除[断开子结点的子目录(childs)]所有(不包括childs的)父联系
             */
            List<Dir_tree> fat_tree = directoryMapper.getAllFatherDirTree(currId);
            List<Integer> childDir = directoryMapper.getAllChildDirId(currId);
            List<Integer> fatherRel = new ArrayList<>();
            for (Dir_tree dir_tree : fat_tree)
                fatherRel.add(dir_tree.getAncestor_id());  //获取当前目录的父目录
//            System.out.println("==================================");
//            System.out.println(fatherRel);
//            System.out.println(childDir);
            if(fatherRel.size() != 0)   //如果没有父目录，没有必要执行[删除父目录的联系]
                directoryMapper.deleteParentDependence(fatherRel, childDir);

            /*
             *  把currId与所有父级进行关联，其中targetId是[直接父级]
             */
            Dir_tree dirTree = new Dir_tree();
            //插入自己与[直接父目录]关联的数据
            dirTree.setAncestor_id(targetId);
            dirTree.setLevel(1);
            dirTree.setDir_id(currId);
            directoryMapper.insertOneRelation(dirTree);
            //查询[直接父目录]的所有父级目录 ([直接父目录]已经和自己关联)
            List<Dir_tree> fatherList = directoryMapper.getAllFatherDirTree(targetId);
            for (Dir_tree f : fatherList)
            {
                Dir_tree t = new Dir_tree();
                t.setDir_id(currId);
                t.setLevel(f.getLevel() + 1);
                t.setAncestor_id(f.getAncestor_id());
                directoryMapper.insertOneRelation(t);
            }
            //查询currId的所有父级目录 (就是上面新添加的dir_tree记录)
            List<Dir_tree> currList = directoryMapper.getAllFatherDirTree(currId);
            //查询currId所有子目录 (所有子目录都要连接上面的dir_tree记录)
            List<Dir_tree> childList = directoryMapper.getAllChildDirTree(currId);

            for (Dir_tree child : childList)    //遍历每个子目录的tree,连接上面新添加的记录
            {
                for (Dir_tree father : currList)    //遍历新添加的记录
                {
                    Dir_tree t = new Dir_tree();
                    t.setAncestor_id(father.getAncestor_id());  //ancestor和新添加的记录是一样的
                    t.setLevel(father.getLevel() + child.getLevel());   //level=dis(target, curr) + dis(curr, child);
                    t.setDir_id(child.getDir_id());     //子目录id
                    directoryMapper.insertOneRelation(t);
                }
            }

            /*
             *  修改dir_item的父id
             */
            directoryMapper.updateDirItemParentId(currId, targetId);
        }
        else        //如果剪切的是File
        {
            //该方法第一个参数才是目录dir
            directoryMapper.updateArtDirId(targetId, currId);
        }
    }

    @Override
    public void hideDocById(int art_id)
    {
        directoryMapper.hideDocById(art_id);
    }


}
