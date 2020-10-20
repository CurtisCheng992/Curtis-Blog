package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.User;
import com.curtis.curtisblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private IUserService userService;

    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpSession session,
                          RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index"; //跳转到登录后主页面
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误！");
            return "redirect:/admin"; //跳转到登录页面11111111111
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin"; //跳转到登录页面
    }
}
