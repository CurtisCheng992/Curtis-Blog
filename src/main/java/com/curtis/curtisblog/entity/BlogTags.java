package com.curtis.curtisblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 博客与其对应的标签的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogTags {

    private Blog blog;
    private List<Tag> tags;
}
