package com.curtis.curtisblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 博客中使用次数前几的类型的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopTypes implements Serializable {

    private Type type;
    private Integer frequency;
}
