package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}
