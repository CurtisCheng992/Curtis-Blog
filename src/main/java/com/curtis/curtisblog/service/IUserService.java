package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.User;

/**
 * 用户的业务层接口
 */
public interface IUserService {

    /**
     * 根据用户名和密码判断用户是否存在
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username,String password);
}
