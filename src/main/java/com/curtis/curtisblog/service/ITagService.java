package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Tag;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * 类型的业务层接口
 */
public interface ITagService {

    /**
     * 新增标签
     * @return
     */
    void saveTag(Tag tag);

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    Tag getTagById(Long id);

    /**
     * 根据标签名称查询标签
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 查询所有标签
     * @return
     */
    List<Tag> listAllTag();

    /**
     * 分页查询标签
     * @return
     */
    PageInfo<Tag> getTagPage(int pageNum, int pageSize);

    /**
     * 根据id修改标签
     * @param tag
     * @return
     */
    void updateTag(Tag tag);

    /**
     * 根据id删除标签
     * @param id
     */
    void deleteTagById(long id);
}
