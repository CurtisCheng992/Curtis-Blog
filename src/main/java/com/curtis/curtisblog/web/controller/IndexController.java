package com.curtis.curtisblog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器
 */
@Controller
public class IndexController {

    /**
     * 跳转到前台主页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 跳转到博客页面
     * @return
     */
    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

}
