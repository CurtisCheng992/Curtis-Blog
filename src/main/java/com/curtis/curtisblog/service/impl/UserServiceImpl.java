package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.mapper.UserMapper;
import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.IUserService;
import com.curtis.curtisblog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户的业务层接口实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, MD5Util.code(password));
    }
}
