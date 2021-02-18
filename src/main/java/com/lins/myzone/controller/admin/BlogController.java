package com.lins.myzoom.controller.admin;

import com.lins.myzoom.pojo.Blog;
import com.lins.myzoom.pojo.BlogQuery;
import com.lins.myzoom.pojo.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * @ClassName BlogController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 15:35
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/admin-publishblog";
    private static final String LIST="admin/admin-blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, BlogQuery blog, Model model,
                        HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, BlogQuery blog, Model model){
        System.out.println(blogService.listBlog(pageable,blog));
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST+" :: blogList";
    }
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if(blog.getId()==null){
            blog1=blogService.saveBlog(blog);
        }else {
            blog1=blogService.updateBlog(blog.getId(),blog);
        }
        if(blog1==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
