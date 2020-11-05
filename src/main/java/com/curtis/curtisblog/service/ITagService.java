package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Tag;
import com.curtis.curtisblog.entity.TopTags;
import com.curtis.curtisblog.entity.TopTypes;
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
     * 根据多个id查询标签
     * @param ids
     * @return
     */
    List<Tag> listTagsByIds(String ids);

    /**
     * 分页查询标签
     * @return
     */
    PageInfo<Tag> getTagPage(int pageNum, int pageSize);

    /**
     * 查询使用次数前几的博客标签
     * @param size
     * @return
     */
    List<TopTags> listTagTop(Integer size);

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
