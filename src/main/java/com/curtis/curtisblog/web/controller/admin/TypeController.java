package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Type;
import com.curtis.curtisblog.service.ITypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 博客类型的控制器
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    /**
     * 分页显示类型
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,Model model) {
        PageInfo<Type> typePageInfo = typeService.getTypePage(pageNum,5);
        model.addAttribute("pageInfo",typePageInfo);
        return "admin/types";
    }

    /**
     * 新增分类
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/types-input";
    }

    /**
     * 提交新增类型
     * @return
     */
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("message","新增失败！不能添加重复的分类！");
            return "redirect:/admin/types";
        }
        typeService.saveType(type);
        Type t = typeService.getTypeByName(type.getName());
        if (t == null){
            attributes.addFlashAttribute("message","新增失败！");
        }else {
            attributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/types";
    }

    /**
     * 更新类型
     * @param type
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("message","更新失败！分类名已存在！");
            return "redirect:/admin/types";
        }
        type.setId(id);
        typeService.updateType(type);
        Type t = typeService.getTypeByName(type.getName());
        if (t == null){
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
         typeService.deleteTypeById(id);
         attributes.addFlashAttribute("message","删除成功！");
         return "redirect:/admin/types";
    }
}
