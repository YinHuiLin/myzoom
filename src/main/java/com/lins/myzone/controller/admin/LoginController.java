package com.lins.myzoom.controller.admin;

import com.lins.myzoom.pojo.Blog;
import com.lins.myzoom.pojo.User;
import com.lins.myzoom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 14:52
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){
        return "admin/admin-login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model){
        User user=userService.checkUser(username,password);
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            model.addAttribute("user",user);
            return "admin/admin-index";
        }
        else {
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
