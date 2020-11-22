package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Comment;
import com.curtis.curtisblog.service.ICommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 评论的控制器
 */
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/comments")
    public String comments(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        model.addAttribute("pageInfo",this.commentService.getCommentPage(pageNum,5));
        return "admin/comments";
    }

    @GetMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        this.commentService.deleteCommentById(id);
        attributes.addFlashAttribute("message","删除评论成功！");
        return "redirect:/admin/comments";
    }

    @GetMapping("/comments/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("comment",this.commentService.getCommentById(id));
        return "admin/comments-edit";
    }

    @PostMapping("/comments/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam("nickname") String nickname , @RequestParam("email") String email , @RequestParam("content") String content , RedirectAttributes attributes){
        this.commentService.updateCommentById(id,nickname,email,content);
        Comment commentById = this.commentService.getCommentById(id);
        boolean nicknameBoolean = StringUtils.equals(commentById.getNickname(), nickname);
        boolean emailBoolean = StringUtils.equals(commentById.getEmail(), email);
        boolean contentBoolean = StringUtils.equals(commentById.getContent(), content);
        if (nicknameBoolean && emailBoolean && contentBoolean){
            attributes.addFlashAttribute("message","修改评论成功！");
        }else {
            attributes.addFlashAttribute("message","修改评论失败！");
        }
        return "redirect:/admin/comments";
    }

}
