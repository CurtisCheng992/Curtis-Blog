package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.mapper.BlogMapper;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博客的业务层接口实现类
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogMapper.findBlogById(id);
    }

    @Transactional
    @Override
    public PageInfo<Blog> getBlogPage(int pageNum, int pageSize, BlogQuery blogQuery) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.listBlogBySearch(blogQuery);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    @Transactional
    @Override
    public void saveBlog(Blog blog) {
        blogMapper.saveBlog(blog);
    }

    @Transactional
    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateBlog(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }
}
