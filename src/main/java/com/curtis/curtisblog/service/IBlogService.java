package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;

/**
 * 博客的业务层接口
 */
public interface IBlogService {

    /**
     * 根据id查询一条博客信息
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 查询博客的分页信息
     * @param pageNum
     * @param pageSize
     * @param blogQuery
     * @return
     */
    PageInfo<Blog> getBlogPage(int pageNum, int pageSize, BlogQuery blogQuery);

    /**
     * 保存博客信息
     * @param blog
     */
    void saveBlog(Blog blog);

    /**
     * 更新博客信息
     * @param blog
     */
    void updateBlog(Blog blog);

    /**
     * 根据id删除博客信息
     * @param id
     */
    void deleteBlog(Long id);
}
