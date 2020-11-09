package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.NotFoundException;
import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.BlogTags;
import com.curtis.curtisblog.entity.Tag;
import com.curtis.curtisblog.mapper.BlogMapper;
import com.curtis.curtisblog.mapper.BlogTagsMapper;
import com.curtis.curtisblog.mapper.CommentMapper;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.utils.MarkdownUtils;
import com.curtis.curtisblog.utils.MyBeanUtils;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 博客的业务层接口实现类
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagsMapper blogTagsMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据id查询博客
     * @param id
     * @return
     */
    @Override
    public Blog getBlog(Long id) {
        Blog blog = blogMapper.findBlogById(id);
        return blog;
    }

    /**
     * 根据id查询一条博客信息
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogMapper.findBlogById(id);
        if (blog == null){
            throw new NotFoundException("该博客不存在！");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        this.blogMapper.updateViews(id);
        return b;
    }

    /**
     * 分页且根据条件查询博客
     * @param pageNum
     * @param pageSize
     * @param blogQuery
     * @return
     */
    @Override
    public PageInfo<Blog> getBlogPageByQuery(int pageNum, int pageSize, BlogQuery blogQuery) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.listBlogBySearch(blogQuery);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 分页查询博客
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Blog> getBlogPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.listAllBlog();
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 根据标签id查询博客的分页信息
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Blog> getBlogPageByTagId(Long tagId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.listBlogByTagId(tagId);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 查询博客的分页信息
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Blog> getBlogPageBySearch(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = this.blogMapper.findByQuery(query);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 按照更新时间顺序查找推荐的前几的博客
     * @param size
     * @return
     */
    @Override
    public List<Blog> listRecommendTopBlog(Integer size) {
        return this.blogMapper.findRecommendTopBlog(size);
    }

    /**
     * 归档博客
     * @return
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = this.blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,this.blogMapper.findByYear(year));
        }
        return map;
    }

    /**
     * 查询博客条数
     * @return
     */
    @Override
    public Long countBlog() {
        return this.blogMapper.countBlog();
    }

    /**
     * 新增博客
     * @param blog
     */
    @Transactional
    @Override
    public void saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //新增博客
        this.blogMapper.saveBlog(blog);
        //新增博客的标签到中间表
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
            this.blogTagsMapper.insertBlogTags(blog.getId(),tag.getId());
        }

    }

    /**
     * 更新博客
     * @param blog
     */
    @Transactional
    @Override
    public void updateBlog(Blog blog) {
        Blog b = blogMapper.findBlogById(blog.getId());
        List<Tag> newTagList = blog.getTags();
        List<Tag> oldTagList = b.getTags();
        if (b == null){
            throw new NotFoundException("该博客不存在！");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        //更新博客
        blogMapper.updateBlog(b);
        //更新博客的标签 中间表; 先删除所有旧的，再插入所有新的
        //删除所有旧的标签
        for (Tag tag : oldTagList) {
            this.blogTagsMapper.deleteBlogTags(blog.getId(),tag.getId());
        }
        //插入所有新的标签
        for (Tag tag : newTagList) {
           this.blogTagsMapper.insertBlogTags(blog.getId(),tag.getId());
        }
    }

    /**
     * 删除博客
     * @param blogId
     */
    @Transactional
    @Override
    public void deleteBlog(Long blogId) {
        //删除博客对应的标签 中间表
        this.blogTagsMapper.deleteAllByBlogId(blogId);
        //删除博客对应的评论
        this.commentMapper.deleteCommentByBlogId(blogId);
        //删除博客
        blogMapper.deleteBlog(blogId);
    }
}
