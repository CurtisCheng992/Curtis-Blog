package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Message;
import com.github.pagehelper.PageInfo;

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

    /**
     * 分页查询留言信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Message> getMessagePage(int pageNum, int pageSize);

    /**
     * 根据留言id删除留言
     * @param id
     */
    void deleteMessageById(Long id);

    /**
     * 根据留言id查询一条留言
     * @param id
     * @return
     */
    Message getMessageById(Long id);

    /**
     * 根据留言id更新留言信息
     * @param id
     * @param nickname
     * @param email
     * @param content
     */
    void updateMessageById(Long id, String nickname, String email, String content);
}
