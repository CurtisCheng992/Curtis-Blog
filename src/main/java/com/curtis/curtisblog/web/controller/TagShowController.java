package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.BlogTags;
import com.curtis.curtisblog.entity.TopTags;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 标签展示的控制器
 */
@Controller
public class TagShowController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                        @PathVariable Long id, Model model){
        int pageSize = 5;  //每页显示条数
        //查出所有标签 返回值有 标签tag和次数frequency
        List<TopTags> topTags = this.tagService.listTagTop(10000);
        if ( id == -1 ){
            id = topTags.get(0).getTag().getId();
        }
        //查出博客的信息，以及该博客的所有标签信息
        PageInfo<Blog> blogs = this.blogService.getBlogPageByTagId(id, pageNum, pageSize);
        model.addAttribute("topTags",topTags);
        model.addAttribute("blogs",blogs);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
