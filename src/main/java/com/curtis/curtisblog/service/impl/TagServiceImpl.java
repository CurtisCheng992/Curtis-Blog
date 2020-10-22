package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Tag;
import com.curtis.curtisblog.mapper.TagMapper;
import com.curtis.curtisblog.service.ITagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博客标签的业务层接口实现类
 */
@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 新增标签
     * @return
     */
    @Transactional
    @Override
    public void saveTag(Tag tag) {
        tagMapper.saveTag(tag);
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Tag getTagById(Long id) {
        return tagMapper.findTagById(id);
    }

    /**
     * 根据标签名称查询标签
     * @param name
     * @return
     */
    public Tag getTagByName(String name){
        return tagMapper.findTagByName(name);
    }

    /**
     * 查询所有标签
     * @return
     */
    public List<Tag> listAllTag(){
        return tagMapper.listAll();
    }

    /**
     * 分页查询标签
     * @return
     */
    @Transactional
    @Override
    public PageInfo<Tag> getTagPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Tag> tags = tagMapper.listAll();
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags);
        return tagPageInfo;
    }

    /**
     * 根据id修改标签
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public void updateTag(Tag tag) {
        tagMapper.updateTag(tag);
    }

    /**
     * 根据id删除标签
     * @param id
     */
    @Transactional
    @Override
    public void deleteTagById(long id) {
        tagMapper.deleteTagById(id);
    }
}
