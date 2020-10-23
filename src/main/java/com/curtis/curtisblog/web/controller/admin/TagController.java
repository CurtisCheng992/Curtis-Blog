package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Tag;
import com.curtis.curtisblog.service.ITagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 博客标签的控制器
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private ITagService tagService;

    /**
     * 分页显示标签
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,Model model) {
        PageInfo<Tag> tagPageInfo = tagService.getTagPage(pageNum,5);
        model.addAttribute("pageInfo",tagPageInfo);
        return "admin/tags";
    }

    /**
     * 新增标签
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/tags-input";
    }

    /**
     * 提交新增标签
     * @return
     */
    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            attributes.addFlashAttribute("message","新增失败！不能添加重复的标签！");
            return "redirect:/admin/tags";
        }
        tagService.saveTag(tag);
        Tag t = tagService.getTagByName(tag.getName());
        if (t == null){
            attributes.addFlashAttribute("message","新增失败！");
        }else {
            attributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 更新标签
     * @param tag
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            attributes.addFlashAttribute("message","更新失败！标签名已存在！");
            return "redirect:/admin/tags";
        }
        tag.setId(id);
        tagService.updateTag(tag);
        Tag t = tagService.getTagByName(tag.getName());
        if (t == null){
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 根据id删除标签
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
         tagService.deleteTagById(id);
         attributes.addFlashAttribute("message","删除成功！");
         return "redirect:/admin/tags";
    }
}
