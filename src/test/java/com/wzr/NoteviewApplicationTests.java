package com.wzr;

import com.wzr.dao.DirectoryMapper;
import com.wzr.dao.UserMapper;
import com.wzr.model.Po.Dir_item;
import com.wzr.service.DirectoryService;
import com.wzr.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class NoteviewApplicationTests
{
    @Autowired
    UserMapper userMapper;

    @Autowired
    DirectoryMapper directoryMapper;

    @Autowired
    DirectoryService directoryService;

    @Test
    void contextLoads()
    {
        Dir_item dirItem = new Dir_item();
        dirItem.setDir_name("test");
        dirItem.setParent_dir_id(1);
        dirItem.setUser_id(1);

        System.out.println("插入前:" + dirItem.getDir_id());
        directoryMapper.insertOneDir(dirItem);
        System.out.println("插入后:" + dirItem.getDir_id());
    }

    @Test
    void testAddDir()
    {
        System.out.println(directoryMapper.getAllFatherDirTree(36));
    }

    @Test
    void testMoveDir()
    {
        directoryService.cutAndMove(2, 7, true);
    }

    @Test
    void testTimeZone(){
        System.out.println(new Date());
    }

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis()
    {
        redisUtil.set("1001", "view", 1);
        redisUtil.incr("1001", "view", 1);
        System.out.println(redisUtil.get("1001", "view"));
    }
}
