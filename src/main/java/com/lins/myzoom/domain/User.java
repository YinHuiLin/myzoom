package com.lins.myzoom.domain;

/**
 * @ClassName User
 * @Description TODO
 * @Author lin
 * @Date 2021/1/30 13:45
 * @Version 1.0
 **/
public class User {
    int id;
    String username;
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
