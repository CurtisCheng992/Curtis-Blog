package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.User;

public interface IUserService {

    User checkUser(String username,String password);
}
