package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Message;

import java.util.List;

public interface IMessageService {

    /**
     * 获取留言列表
     * @return
     */
    List<Message> listMessage();

    /**
     * 保存留言
     * @param message
     */
    void saveMessage(Message message);

    /**
     * 获取留言条数
     * @return
     */
    Integer countMessage();
}
