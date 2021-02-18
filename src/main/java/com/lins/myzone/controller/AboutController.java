package com.lins.myzoom.controller;

import com.lins.myzoom.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName AboutController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/8 12:03
 * @Version 1.0
 **/
@Controller
public class AboutController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/about")
    public String about(Model model){
//        model.addAttribute("user")
        return "aboutme";
    }
}
