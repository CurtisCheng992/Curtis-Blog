package com.curtis.curtisblog;

import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurtisBlogApplication.class)
public class TestUser {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testCheck(){
        User user = userService.checkUser("Curtis", "123456");
        System.out.println(user);
    }
}
