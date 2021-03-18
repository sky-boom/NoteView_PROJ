package com.wzr.dao;

import com.wzr.model.Po.Dir_item;
import com.wzr.model.Po.Dir_tree;
import com.wzr.model.Vo.DirectoryTreeNode;
import com.wzr.model.Vo.Doc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryMapper
{
    /**
     * ***********************  查 Query  *************************
     */
    //获取该用户所有dir_tree记录
    List<Dir_tree> getAllDirTreeRecord(int user_id);

    //获取dir_item记录，添上child，用于构造目录树
    List<DirectoryTreeNode> getAllDirItemNodeRecord(int user_id);

    //获取某个目录的所有父级
    List<Dir_tree> getAllFatherDirTree(int dir_id);

    //获取某个目录的所有子级
    List<Dir_tree> getAllChildDirTree(int dir_id);

    //获取某个目录的所有子级id, 包括自身
    List<Integer> getAllChildDirId(int dir_id);

//    //根据article id查File所需的title和update_time
//    Article_UpdTit getUpdAndTitById(int dir_id);

    //某个目录id下的所有file
    List<Doc> queryDocByDirId(int dir_id);

    //某个用户所有doc
    List<Doc> queryAllDocByUserId(int user_id);

    /**
     * ***********************  增 Insert  *************************
     */
    //insert一条dir_item记录
    int insertOneDir(Dir_item dir_item);

    //insert一条dir_tree记录
    int insertOneRelation(Dir_tree dir_tree);

//    //insert一条file记录
//    int insertOneFile(File file);

    /**
     * ***********************  删 delete  *************************
     */
    //删除某目录所有记录(其子目录也同时删除)
    void deleteDirById(int dir_id);

    //删除某目录(包括其子目录)的父联系(是指该目录以上的父联系)
    int deleteParentDependence(List<Integer> fatherRel, List<Integer> childDir);

    /**
     * ***********************  改 update  *************************
     */
    //重命名目录
    int updateDirName(@Param("dir_id") int dir_id, @Param("name") String new_name);

    //修改dir_item的父id
    int updateDirItemParentId(@Param("dir_id") int dir_id,
                              @Param("new_parent_id") int new_parent_id);

//    //修改file的父id
//    int updateFileParentId(@Param("art_id") int art_id,
//                           @Param("new_parent_id") int new_parent_id);

    //隐藏某个doc，也就是把它设置为-1
    void hideDocById(int art_id);

    //根据dir_id和art_id修改article的父目录
    void updateArtDirId(int dir_id, int art_id);
}
