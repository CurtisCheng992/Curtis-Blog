package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户的持久层接口
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    @Select("select * from t_user where username = #{username} and password = #{password}")
    @Results(id = "userMap",
            value = {
                @Result(id = true,column = "id",property = "id"),
                @Result(column = "avatar",property = "avatar"),
                @Result(column = "create_time",property = "createTime"),
                @Result(column = "email",property = "email"),
                @Result(column = "nickname",property = "nickname"),
                @Result(column = "password",property = "password"),
                @Result(column = "type",property = "type"),
                @Result(column = "update_time",property = "updateTime"),
                @Result(column = "username",property = "username"),
            })
    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from t_user where id = #{id}")
    @ResultMap("userMap")
    User findUserById(@Param("id") Long id);
}
