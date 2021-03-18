package com.wzr.service;

import com.wzr.model.Po.User_auth;

public interface UserService
{
    //登录验证
    User_auth queryByName(String name);

    //通过用户名获取id
    int queryIdByName(String name);
}
