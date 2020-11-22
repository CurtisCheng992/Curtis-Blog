package com.curtis.curtisblog.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Image implements Serializable {

    private Long id;            // 图片id
    private String link;        // 图片链接
    private String description; // 图片描述
    private String fileName;    // 文件名
    private String fileSize;    // 文件大小
    private Date uploadTime;    // 上传时间
    private Date updateTime;    // 更新时间

}
