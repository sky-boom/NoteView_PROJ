package com.wzr.service.impl;

import com.wzr.dao.UserMapper;
import com.wzr.model.Po.User_auth;
import com.wzr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired    //service调用dao
    UserMapper userMapper;

    @Override
    public User_auth queryByName(String name)
    {
        return userMapper.queryByName(name);
    }

    @Override
    public int queryIdByName(String name)
    {
        return userMapper.queryIdByName(name);
    }
}
