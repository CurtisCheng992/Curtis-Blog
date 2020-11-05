package com.curtis.curtisblog;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.Tag;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITagService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurtisBlogApplication.class)
public class TestBlogService {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITagService tagService;

    @Test
    public void testGetBlog(){
        Blog blog = this.blogService.getBlog(8l);
        System.out.println(blog);
        System.out.println(blog.getTags());
    }

    @Test
    public void testPage(){
        PageInfo<Blog> blogPage = this.blogService.getBlogPage(2, 3, null);
        for (Blog blog : blogPage.getList()) {
            System.out.println("blog:"+blog);
            System.out.println("tags:"+blog.getTags());
            System.out.println("type:"+blog.getType().getName());
        }
    }

    @Test
    public void testSaveBlog(){
       Blog blog = new Blog();
       blog.setTitle("TestSaveBlog_Title");
       blog.setContent("TestSaveBlog_Content");
       List<Tag> tagList = new ArrayList<>();
       Tag tag = this.tagService.getTagById(1l);
       tagList.add(tag);
       tag = this.tagService.getTagById(2l);
       tagList.add(tag);
       blog.setTags(tagList);
       this.blogService.saveBlog(blog);
    }

    @Test
    public void testupdateBlog(){
        Blog blog = this.blogService.getBlog(20l);
        blog.setTitle("TestUpdateBlog_Title");
        blog.setContent("TestUpdateBlog_Content");
        List<Tag> tagList = new ArrayList<>();
        Tag tag1 = this.tagService.getTagById(3l);
        tagList.add(tag1);
        Tag tag2 = this.tagService.getTagById(4l);
        tagList.add(tag2);
        blog.setTags(tagList);
        this.blogService.updateBlog(blog);
    }

    @Test
    public void testDeleteBlog(){
        this.blogService.deleteBlog(20l);
    }
}
