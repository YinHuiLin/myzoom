package com.lins.myzoom.controller;

import com.lins.myzoom.pojo.Blog;
import com.lins.myzoom.pojo.Type;
import com.lins.myzoom.pojo.Tag;
import com.lins.myzoom.service.BlogService;
import com.lins.myzoom.service.TagService;
import com.lins.myzoom.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author lin
 * @Date 2021/1/30 22:03
 * @Version 1.0
 **/
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping(value = "/")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        List<Type> types=typeService.listTop(6);
        model.addAttribute("types",types);
        List<Tag> tags=tagService.listTop(10);
        model.addAttribute("tags",tags);
        List<Blog> blogs=blogService.listRecommendTop(8);
        model.addAttribute("recommendBlogs",blogs);
        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                     Pageable pageable,
                         @RequestParam String query,Model model){
        model.addAttribute("page",blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping(value = "/blog/{id}")
    public String blog(@PathVariable Long id,Model model)
    {
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        List<Blog> blogs=blogService.listRecommendTop(3);
        model.addAttribute("newBlogs",blogs);
        return "_fragments :: newbloglist";
    }
}
