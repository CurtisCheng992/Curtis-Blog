package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITypeService;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 博客的控制器
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                        BlogQuery blogQuery, Model model){
        model.addAttribute("types",typeService.listAllType());
        PageInfo<Blog> blogPageInfo = blogService.getBlogPage(pageNum, 5, blogQuery);
        model.addAttribute("pageInfo",blogPageInfo);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                         BlogQuery blogQuery, Model model){
        PageInfo<Blog> blogPageInfo = blogService.getBlogPage(pageNum, 5, blogQuery);
        model.addAttribute("pageInfo",blogPageInfo);
        return "admin/blogs :: blogList";
    }
}
