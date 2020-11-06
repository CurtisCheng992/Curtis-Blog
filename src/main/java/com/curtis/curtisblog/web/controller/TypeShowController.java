package com.curtis.curtisblog.web.controller;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.TopTypes;
import com.curtis.curtisblog.entity.Type;
import com.curtis.curtisblog.service.IBlogService;
import com.curtis.curtisblog.service.ITypeService;
import com.curtis.curtisblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类展示的控制器
 */
@Controller
public class TypeShowController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IBlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                        @PathVariable Long id, Model model){
        List<TopTypes> topTypes = this.typeService.listTypeTop(10000);
        if ( id == -1 ){
            id = topTypes.get(0).getType().getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("topTypes",topTypes);
        model.addAttribute("page",this.blogService.getBlogPageByQuery(pageNum,8,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
