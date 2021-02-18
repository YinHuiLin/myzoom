package com.lins.myzoom.service.impl;

import com.lins.myzoom.dao.UserRepository;
import com.lins.myzoom.pojo.User;
import com.lins.myzoom.service.UserService;
import com.lins.myzoom.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 14:46
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
