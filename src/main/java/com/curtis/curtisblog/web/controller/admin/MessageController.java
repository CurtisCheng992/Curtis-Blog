package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Message;
import com.curtis.curtisblog.service.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 留言的控制器
 */
@Controller
@RequestMapping("/admin")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @GetMapping("/messages")
    public String messages(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        model.addAttribute("pageInfo",this.messageService.getMessagePage(pageNum,5));
        return "admin/messages";
    }

    @GetMapping("/messages/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        this.messageService.deleteMessageById(id);
        attributes.addFlashAttribute("message","删除留言成功！");
        return "redirect:/admin/messages";
    }

    @GetMapping("/messages/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("Message",this.messageService.getMessageById(id));
        return "admin/messages-edit";
    }

    @PostMapping("/messages/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam("nickname") String nickname , @RequestParam("email") String email , @RequestParam("content") String content , RedirectAttributes attributes){
        this.messageService.updateMessageById(id,nickname,email,content);
        Message messageById = this.messageService.getMessageById(id);
        boolean nicknameBoolean = StringUtils.equals(messageById.getNickname(), nickname);
        boolean emailBoolean = StringUtils.equals(messageById.getEmail(), email);
        boolean contentBoolean = StringUtils.equals(messageById.getContent(), content);
        if (nicknameBoolean && emailBoolean && contentBoolean){
            attributes.addFlashAttribute("message","修改留言成功！");
        }else {
            attributes.addFlashAttribute("message","修改留言失败！");
        }
        return "redirect:/admin/messages";
    }
}
