package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITagService;
import com.curtis.curtisblog.service.ITypeService;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

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
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String save(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(this.typeService.getTypeById(blog.getType().getId()));
        blog.setTags(this.tagService.listTagsByIds(blog.getTagIds()));
        if (ObjectUtils.isEmpty(blog.getId())){
            //不含博客id，则为新增
            this.blogService.saveBlog(blog);
        }else{
            //含有博客id，则为修改
            this.blogService.updateBlog(blog);
        }

        Blog b = blogService.getBlog(blog.getId());
        if (b == null){
            attributes.addFlashAttribute("message","操作失败！");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        this.blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功！");
        return REDIRECT_LIST;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listAllType());
        model.addAttribute("tags",tagService.listAllTag());
    }
}
