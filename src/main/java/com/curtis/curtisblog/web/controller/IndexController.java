package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITagService;
import com.curtis.curtisblog.service.ITypeService;
import com.curtis.curtisblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 主页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    /**
     * 跳转到前台主页
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                        Model model){
        Integer pageSize = 10;
        Integer topTypeSize = 6;
        Integer topTagSize = 10;
        Integer topRecommendBlogSize = 8;
        model.addAttribute("page",this.blogService.getBlogPage(pageNum,pageSize));
        model.addAttribute("types",this.typeService.listTypeTop(topTypeSize));
        model.addAttribute("tags",this.tagService.listTagTop(topTagSize));
        model.addAttribute("recommendBlogs",this.blogService.listRecommendTopBlog(topRecommendBlogSize));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                         @RequestParam String query, Model model){
        Integer pageSize = 10;
        model.addAttribute("page",this.blogService.getBlogPageBySearch("%"+query+"%",pageNum,pageSize));
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * 跳转到博客页面
     * @return
     */
    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

}
