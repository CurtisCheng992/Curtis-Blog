package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Tag;
import com.curtis.curtisblog.entity.TopTags;
import com.curtis.curtisblog.mapper.BlogTagsMapper;
import com.curtis.curtisblog.mapper.TagMapper;
import com.curtis.curtisblog.service.ITagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客标签的业务层接口实现类
 */
@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagsMapper blogTagsMapper;

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
    @Override
    public Tag getTagById(Long id) {
        return tagMapper.findTagById(id);
    }

    /**
     * 根据标签名称查询标签
     * @param name
     * @return
     */
    @Override
    public Tag getTagByName(String name){
        return tagMapper.findTagByName(name);
    }

    /**
     * 查询所有标签
     * @return
     */
    @Override
    public List<Tag> listAllTag(){
        return tagMapper.listAll();
    }

    /**
     * 根据多个id查询标签
     * @param ids
     * @return
     */
    @Override
    public List<Tag> listTagsByIds(String ids) {
        List<Tag> tags = new ArrayList<>();
        List<Long> idsList = convertToList(ids);
        for (int i = 0; i < idsList.size(); i++){
            Tag tag = tagMapper.findTagById(idsList.get(i));
            tags.add(tag);
        }
        return tags;
    }

    /**
     * 分页查询标签
     * @return
     */
    @Override
    public PageInfo<Tag> getTagPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Tag> tags = tagMapper.listAll();
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags);
        return tagPageInfo;
    }

    /**
     * 查询使用次数前几的博客标签
     * @param size
     * @return
     */
    @Override
    public List<TopTags> listTagTop(Integer size) {
        return this.blogTagsMapper.findTopTags(size);
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

    /**
     * 把字符串转换为数组
     * @param ids
     * @return
     */
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
