package com.lins.myzoom.service;

import com.lins.myzoom.pojo.User;

public interface UserService {
    User checkUser(String username,String password);
}
