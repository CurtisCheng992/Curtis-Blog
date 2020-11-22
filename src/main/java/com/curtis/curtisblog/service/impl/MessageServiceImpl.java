package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Message;
import com.curtis.curtisblog.mapper.MessageMapper;
import com.curtis.curtisblog.service.IMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 留言的业务层接口实现类
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> listMessage() {
        List<Message> messages = this.messageMapper.listAllMessage();
        return eachMessage(messages);
    }

    @Transactional
    @Override
    public void saveMessage(Message message) {
        Long parentMessageId = message.getParentMessage().getId();
        if (parentMessageId != -1){
            message.setParentMessage(this.messageMapper.findParentByParentId(parentMessageId));
        }else{
            message.setParentMessage(null);
        }
        message.setCreateTime(new Date());
        this.messageMapper.saveMessage(message);
    }

    /**
     * 获取留言条数
     * @return
     */
    @Override
    public Integer countMessage() {
        return this.messageMapper.countMessage();
    }

    /**
     * 分页查询留言信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Message> getMessagePage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Message> messages = messageMapper.listAllMessages();
        PageInfo<Message> messagePageInfo = new PageInfo<>(messages);
        return messagePageInfo;
    }

    /**
     * 根据留言id删除留言
     * @param id
     */
    @Transactional
    @Override
    public void deleteMessageById(Long id) {
        this.messageMapper.deleteById(id);
    }

    /**
     * 根据留言id查询一条留言
     * @param id
     * @return
     */
    @Override
    public Message getMessageById(Long id) {
        return this.messageMapper.findById(id);
    }

    @Transactional
    @Override
    public void updateMessageById(Long id, String nickname, String email, String content) {
        this.messageMapper.updateMessageById(id,nickname,email,content);
    }

    /**
     * 循环每个顶级的留言节点
     * @param messages
     * @return
     */
    private List<Message> eachMessage(List<Message> messages) {
        List<Message> messagesView = new ArrayList<>();
        for (Message message : messages) {
            Message m = new Message();
            BeanUtils.copyProperties(message,m);
            messagesView.add(m);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(messagesView);
        return messagesView;
    }

    /**
     *
     * @param messages root根节点的对象集合
     * @return
     */
    private void combineChildren(List<Message> messages) {

        for (Message message : messages) {
            List<Message> replys1 = message.getReplyMessages();
            for(Message reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            message.setReplyMessages(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param message 被迭代的对象
     * @return
     */
    private void recursively(Message message) {
        tempReplys.add(message);//顶节点添加到临时存放集合
        if (message.getReplyMessages().size()>0) {
            List<Message> replys = message.getReplyMessages();
            for (Message reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyMessages().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
