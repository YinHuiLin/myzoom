package com.lins.myzoom.controller;

import com.lins.myzoom.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName ArchivesController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/8 10:23
 * @Version 1.0
 **/
@Controller
public class ArchivesController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archivesBlog());
        model.addAttribute("blogCount",blogService.countAllPublishedBlog());
        return "archives";
    }
}
