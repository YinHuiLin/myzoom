package com.lins.myzoom.controller;

import com.lins.myzoom.domain.User;
import com.lins.myzoom.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;


/**
 * @ClassName LoginController
 * @Description TODO
 * @Author lin
 * @Date 2021/1/30 13:58
 * @Version 1.0
 **/
@Controller
public class LoginController {
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User user){
        String username=user.getUsername();
        //对html标签进行转义，防止xss攻击
        username= HtmlUtils.htmlEscape(username);
        if(!Objects.equals("admin",username)||!Objects.equals("123",user.getPassword())){
            String message="账号密码错误";
            System.out.println("test");
            return new Result(400);
        }
        else {
            return new Result(200);
        }
    }
}
