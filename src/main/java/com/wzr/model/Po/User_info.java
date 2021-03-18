package com.wzr.model.Po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  唯一用户
 */
public class User_info
{
    private int user_id;
    private String nickname;
    private String sex;

    public User_info()
    {
    }

    public User_info(int user_id, String nickname, String sex)
    {
        this.user_id = user_id;
        this.nickname = nickname;
        this.sex = sex;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    @Override
    public String toString()
    {
        return "User_info{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
