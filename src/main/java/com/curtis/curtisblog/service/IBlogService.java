package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
     * 获取并转换博客内容
     * @param id
     * @return
     */
    Blog getAndConvert(Long id);

    /**
     * 根据查询条件查询博客的分页信息
     * @param pageNum
     * @param pageSize
     * @param blogQuery
     * @return
     */
    PageInfo<Blog> getBlogPageByQuery(int pageNum, int pageSize, BlogQuery blogQuery);

    /**
     * 查询博客的分页信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Blog> getBlogPage(int pageNum, int pageSize);

    /**
     * 查询博客的分页信息
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Blog> getBlogPageBySearch(String query,int pageNum, int pageSize);

    /**
     * 按照更新时间顺序查找推荐的前几的博客
     * @param size
     * @return
     */
    List<Blog> listRecommendTopBlog(Integer size);

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
