package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.entity.Comment;
import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 评论的控制器
 */
@Controller
public class CommentShowController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IBlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",this.commentService.listCommentByBlogId(blogId));
        model.addAttribute("commentsCount",this.commentService.CountCommentsByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlogId();
        comment.setBlogId(blogId);
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        this.commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
