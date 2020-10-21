package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录的控制器
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private IUserService userService;

    @GetMapping()
    public String loginPage(){ //跳转到登录页面
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,//用户登录
                          @RequestParam("password") String password,
                          HttpSession session,
                          RedirectAttributes attributes){
        User user = userService.checkUser(username, password); //查询用户是否存在
        if (user != null){ //若用户不为空，即登录成功
            user.setPassword(null); //屏蔽密码，避免明文传输泄露密码
            session.setAttribute("user",user);
            return "admin/index"; //跳转到登录后主页面
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误！");
            return "redirect:/admin"; //跳转到登录页面
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin"; //跳转到登录页面
    }
}
