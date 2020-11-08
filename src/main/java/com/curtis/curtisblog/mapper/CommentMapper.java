package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 评论的mapper
 */
@Mapper
public interface CommentMapper {

    @Results(id = "commentMap",
            value = {
                @Result(id = true,column = "id",property = "id"),
                @Result(column = "avatar",property = "avatar"),
                @Result(column = "content",property = "content"),
                @Result(column = "create_time",property = "createTime"),
                @Result(column = "email",property = "email"),
                @Result(column = "nickname",property = "nickname"),
                @Result(column = "admin_comment",property = "adminComment"),
                @Result(column = "blog_id",property = "blogId"),
                @Result(column = "parent_comment_id",property = "parentComment",
                    one = @One(select = "com.curtis.curtisblog.mapper.CommentMapper.findParentByParentId",fetchType = FetchType.EAGER)),
                @Result(column = "id", property = "replyComments",
                    many = @Many(select = "com.curtis.curtisblog.mapper.CommentMapper.findReplyListByCid",fetchType = FetchType.LAZY)),
    })
    @Select("select * from t_comment where blog_id = #{blogId} and parent_comment_id is null order by create_time asc")
    List<Comment> findByBlogId(Long blogId);

    @ResultMap("commentMap")
    @Select("select * from t_comment where id = #{parentCommentId}")
    Comment findParentByParentId(Long parentCommentId);

    @ResultMap("commentMap")
    @Select("select t1.* from t_comment t1, t_comment t2 where t1.parent_comment_id = t2.id and t2.id = #{id}")
    List<Comment> findReplyListByCid(Long id);

    @Insert("insert into t_comment values (#{comment.id},#{comment.avatar},#{comment.content},#{comment.createTime},#{comment.email},#{comment.nickname},#{comment.blog.id},#{comment.parentComment.id},#{comment.adminComment})")
    void saveComment(@Param("comment") Comment comment);

    @Delete("delete from t_comment where id = #{id}")
    void deleteCommentById(Long id);

    @Delete("delete from t_comment where blog_id = #{blogId}")
    void deleteCommentByBlogId(Long blogId);
}
