package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 留言的mapper
 */
@Mapper
public interface MessageMapper {

    @Results(id = "messageMap",
            value = {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "avatar",property = "avatar"),
                    @Result(column = "content",property = "content"),
                    @Result(column = "create_time",property = "createTime"),
                    @Result(column = "email",property = "email"),
                    @Result(column = "nickname",property = "nickname"),
                    @Result(column = "admin_message",property = "adminMessage"),
                    @Result(column = "blog_id",property = "blogId"),
                    @Result(column = "parent_message_id",property = "parentMessage",
                            one = @One(select = "com.curtis.curtisblog.mapper.MessageMapper.findParentByParentId",fetchType = FetchType.EAGER)),
                    @Result(column = "id", property = "replyMessages",
                            many = @Many(select = "com.curtis.curtisblog.mapper.MessageMapper.findReplyListByMid",fetchType = FetchType.LAZY)),
            })
    @Select("select * from t_message where parent_message_id is null order by create_time asc")
    List<Message> listAllMessage();

    @ResultMap("messageMap")
    @Select("select * from t_message where id = #{parentMessageId}")
    Message findParentByParentId(Long parentMessageId);

    @ResultMap("messageMap")
    @Select("select t1.* from t_message t1, t_message t2 where t1.parent_message_id = t2.id and t2.id = #{mid}")
    List<Message> findReplyListByMid(Long mid);

    @Insert("insert into t_message values (#{message.id},#{message.avatar},#{message.content},#{message.createTime},#{message.email},#{message.nickname},#{message.parentMessage.id},#{message.adminMessage})")
    void saveMessage(@Param("message") Message message);

    @Select("select count(1) from t_message")
    Integer countMessage();
}
