package com.curtis.curtisblog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客类型的实体类
 */
public class Type implements Serializable {

    private Long id; //类型的id
    private String name; //类型名

    private List<Blog> blogs = new ArrayList<>(); //所属的博客

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
