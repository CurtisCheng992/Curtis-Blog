package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Comment;
import com.curtis.curtisblog.mapper.CommentMapper;
import com.curtis.curtisblog.service.ICommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论的业务层接口实现类
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据博客id获取评论列表
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = this.commentMapper.findByBlogId(blogId);
        return eachComment(comments);
    }

    /**
     * 保存博客
     * @param comment
     */
    @Transactional
    @Override
    public void saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(this.commentMapper.findParentByParentId(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        this.commentMapper.saveComment(comment);
    }

    /**
     * 根据博客id获取评论总数
     * @param blogId
     * @return
     */
    @Override
    public Integer CountCommentsByBlogId(Long blogId) {
        return this.commentMapper.countCommentsByBlogId(blogId);
    }

    /**
     * 查询分页评论信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Comment> getCommentPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments = this.commentMapper.listAllComments();
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return commentPageInfo;
    }

    /**
     * 根据评论id删除评论
     * @param id
     */
    @Transactional
    @Override
    public void deleteCommentById(Long id) {
        this.commentMapper.deleteCommentById(id);
    }

    /**
     * 根据评论id查询评论信息
     * @param id
     * @return
     */
    @Override
    public Comment getCommentById(Long id) {
        return this.commentMapper.findById(id);
    }

    /**
     * 根据评论id修改评论信息
     * @param id
     * @param nickname
     * @param email
     * @param content
     */
    @Transactional
    @Override
    public void updateCommentById(Long id, String nickname, String email, String content) {
        this.commentMapper.updateById(id,nickname,email,content);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
