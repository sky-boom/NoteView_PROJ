package com.wzr.dao;

import com.wzr.model.Po.User_auth;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper
{
    //登录验证
    User_auth queryByName(String name);

    //通过用户名获取id
    int queryIdByName(String name);
}
