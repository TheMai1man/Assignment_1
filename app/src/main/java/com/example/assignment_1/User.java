package com.example.assignment_1;

public class User
{
    private final String email, pwd;
    private final int id;

    public User (String email, String pwd, int id)
    {
        this.email = email;
        this.pwd = pwd;
        this.id = id;
    }

    public String getEmail()
    {
        return this.email;
    }
    public String getPwd()
    {
        return this.pwd;
    }
    public int getId()
    {
        return this.id;
    }

}
