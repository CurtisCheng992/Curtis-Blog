package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 评论的业务层接口
 */
public interface ICommentService {

    /**
     * 获取评论列表
     * @param id
     * @return
     */
    List<Comment> listCommentByBlogId(Long id);

    /**
     * 保存评论
     * @param comment
     */
    void saveComment(Comment comment);

    /**
     * 根据博客id获取评论总数
     * @param blogId
     * @return
     */
    Integer CountCommentsByBlogId(Long blogId);

    /**
     * 查询评论分页信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Comment> getCommentPage(int pageNum, int pageSize);

    /**
     * 根据评论id删除评论
     * @param id
     */
    void deleteCommentById(Long id);

    /**
     * 根据评论id查询评论信息
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 根据评论id修改评论信息
     * @param id
     * @param nickname
     * @param email
     * @param content
     */
    void updateCommentById(Long id, String nickname, String email, String content);
}
