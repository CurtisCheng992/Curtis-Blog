package com.curtis.curtisblog;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.Type;
import com.curtis.curtisblog.mapper.BlogMapper;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurtisBlogApplication.class)
public class TestBlog {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private IBlogService blogService;

    @Test
    public void testListBlogBySearch(){
        BlogQuery blogQuery = new BlogQuery("456",null,null);
        List<Blog> blogs = blogMapper.listBlogBySearch(blogQuery);
        for (Blog blog : blogs) {
            System.out.println(blog);
            System.out.println(blog.getType().toString());
            System.out.println(blog.getUser().toString());
        }
    }

    @Test
    public void testListAll(){
        List<Blog> blogs = blogMapper.listAllBlog();
        for (Blog blog : blogs) {
            System.out.println(blog);
            System.out.println(blog.getType().toString());
            System.out.println(blog.getUser().toString());
        }
    }

    @Test
    public void testPageSearch(){
        BlogQuery blogQuery = new BlogQuery("456",1l,false);
        PageInfo<Blog> blogPageInfo = blogService.getBlogPage(1, 5, blogQuery);
        System.out.println("blogPageInfo: "+blogPageInfo);
        List<Blog> list = blogPageInfo.getList();
        for (Blog blog : list) {
            System.out.println(blog);
        }
    }
}
