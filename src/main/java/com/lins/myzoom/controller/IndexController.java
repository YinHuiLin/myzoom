package com.lins.myzoom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author lin
 * @Date 2021/1/30 22:03
 * @Version 1.0
 **/
@Controller
public class IndexController {
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
