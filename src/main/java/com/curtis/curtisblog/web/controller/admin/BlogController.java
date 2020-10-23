package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITagService;
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

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    /**
     * 分页查询博客信息
     * @param pageNum
     * @param blogQuery
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                        BlogQuery blogQuery, Model model){
        model.addAttribute("types",typeService.listAllType());
        PageInfo<Blog> blogPageInfo = blogService.getBlogPage(pageNum, 5, blogQuery);
        model.addAttribute("pageInfo",blogPageInfo);
        return LIST;
    }

    /**
     * 根据查询条件分页查询博客信息
     * @param pageNum
     * @param blogQuery
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                         BlogQuery blogQuery, Model model){
        PageInfo<Blog> blogPageInfo = blogService.getBlogPage(pageNum, 5, blogQuery);
        model.addAttribute("pageInfo",blogPageInfo);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listAllType());
        model.addAttribute("tags",tagService.listAllTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
}
