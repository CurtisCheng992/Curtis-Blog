package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.entity.Message;
import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

/**
 * 留言板展示的控制器
 */
@Controller
public class MessageShowController {

    @Autowired
    private IMessageService messageService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/messages")
    public String message(Model model){
        model.addAttribute("messagesCount",this.messageService.countMessage());
        model.addAttribute("messages",this.messageService.listMessage());
        return "message";
    }

    @GetMapping("/messageShow")
    public String messageShow(Model model){
        model.addAttribute("messagesCount",this.messageService.countMessage());
        model.addAttribute("messages",this.messageService.listMessage());
        return "message :: messageList";
    }

    @PostMapping("/messages")
    public String post(Message message, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null){
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(true);
        }else{
            message.setAvatar(avatar);
        }
        this.messageService.saveMessage(message);
        return "redirect:/messageShow";
    }
}
