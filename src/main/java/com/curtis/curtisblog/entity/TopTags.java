package com.curtis.curtisblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客中使用次数前几的博客标签的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopTags {

    private Tag tag;
    private Integer frequency;
}
