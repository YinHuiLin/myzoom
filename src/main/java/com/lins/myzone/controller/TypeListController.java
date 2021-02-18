package com.lins.myzoom.controller;

import com.lins.myzoom.pojo.BlogQuery;
import com.lins.myzoom.pojo.Type;
import com.lins.myzoom.service.BlogService;
import com.lins.myzoom.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName TypeController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/7 21:40
 * @Version 1.0
 **/
@Controller
public class TypeListController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.ASC)
                                    Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types=typeService.listTop(1000);//数目指定的足够大就不会限制查询
        if(id==-1){
            id=types.get(0).getId();
        }
        for(Type type:types){
            type.setPublishedBlogCount(typeService.publishedBlogsCount(type.getId()));
        }
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlogByType(pageable,id));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
