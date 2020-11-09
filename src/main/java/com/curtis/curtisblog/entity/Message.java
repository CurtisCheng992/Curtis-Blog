package com.curtis.curtisblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 留言板的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message implements Serializable {

    private Long id; // 留言id
    private String nickname; // 留言者的昵称
    private String email; // 留言者的电子邮件地址
    private String content; // 留言内容
    private String avatar; // 留言的头像
    private Date createTime; // 留言创建时间
    private List<Message> replyMessages = new ArrayList<>(); // 回复留言
    private Message parentMessage; // 父留言
    private boolean adminMessage; // 管理员留言

}
