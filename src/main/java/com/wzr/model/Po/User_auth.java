package com.wzr.model.Po;
/**
 *  用户验证
 */
public class User_auth
{
    private int id;
    private int user_id;
    private String identity_type;
    private String account;
    private String password;

    public User_auth()
    {
    }

    public User_auth(int id, int user_id, String identity_type, String account, String password)
    {
        this.id = id;
        this.user_id = user_id;
        this.identity_type = identity_type;
        this.account = account;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getIdentity_type()
    {
        return identity_type;
    }

    public void setIdentity_type(String identity_type)
    {
        this.identity_type = identity_type;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "User_auth{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", identity_type='" + identity_type + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
