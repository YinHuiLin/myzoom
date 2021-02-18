package com.lins.myzoom.controller.admin;

import com.lins.myzoom.pojo.Type;
import com.lins.myzoom.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @ClassName TypeController
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 16:14
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model,
                        HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/admin-types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/admin-addtype";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/admin-addtype";
    }
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //type与bindingresult两个参数要挨着，否则验证不起作用
        Type type2=typeService.getTypeByName(type.getName());
        if(type2!=null){
            result.rejectValue("name","nameError","分类已存在");
        }
        if(result.hasErrors()){
            return "admin/admin-addtype";
        }
        Type type1=typeService.saveType(type);
        if(type1==null){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type2=typeService.getTypeByName(type.getName());
        if(type2!=null){
            result.rejectValue("name","nameError","分类已存在");
        }
        if(result.hasErrors()){
            return "admin/admin-addtype";
        }
        Type type1=typeService.updateType(id,type);
        if(type1==null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
