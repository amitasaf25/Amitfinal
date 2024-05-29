package com.example.amitfinal.Repository.Moudle;

public class User
{
    private String password,email, name;


    public User(String email, String password)
    {

        this.email = email;
        this.password = password;
    }
    public User(String email, String password,String name)
    {

        this.email = email;
        this.password = password;
        this.name=name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
