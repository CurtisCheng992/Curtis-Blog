package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITagService;
import com.curtis.curtisblog.service.ITypeService;
import com.curtis.curtisblog.vo.BlogQuery;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        Integer pageSize = 10;      //每页显示条数
        Integer topTypeSize = 6;    //靠前分类显示条数
        Integer topTagSize = 10;    //靠前标签显示条数
        Integer topRecommendBlogSize = 8; //靠前推荐博客显示条数
        model.addAttribute("page",this.blogService.getBlogPage(pageNum,pageSize));
        model.addAttribute("types",this.typeService.listTypeTop(topTypeSize));
        model.addAttribute("tags",this.tagService.listTagTop(topTagSize));
        model.addAttribute("recommendBlogs",this.blogService.listRecommendTopBlog(topRecommendBlogSize));
        return "index";
    }

    /**
     * 简单的全局搜索
     * @param pageNum
     * @param query
     * @param model
     * @return
     */
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
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        Blog blog = this.blogService.getAndConvert(id);
        model.addAttribute("blog", blog);
        model.addAttribute("commentsCount", blog.getComments().size());
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",this.blogService.listRecommendTopBlog(3));
        return "_fragments :: newBlogList";
    }

}
