package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Comment;

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
}
