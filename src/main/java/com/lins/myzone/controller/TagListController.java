package com.lins.myzoom.controller;

import com.lins.myzoom.pojo.BlogQuery;
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

import java.util.List;

/**
 * @ClassName TypeController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/7 21:40
 * @Version 1.0
 **/
@Controller
public class TagListController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.ASC)
                                    Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags=tagService.listTop(1000);//数目指定的足够大就不会限制查询
        if(id==-1){
            id=tags.get(0).getId();
        }
        for (Tag tag:tags){
            tag.setPublishedBlogCount(tagService.publishedBlogsCount(tag.getId()));
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlogByTag(pageable,id));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
