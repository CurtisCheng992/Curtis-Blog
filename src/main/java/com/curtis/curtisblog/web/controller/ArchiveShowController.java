package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 归档显示的控制器
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private IBlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",this.blogService.archiveBlog());
        model.addAttribute("blogCount",this.blogService.countBlog());
        return "archives";
    }
}
