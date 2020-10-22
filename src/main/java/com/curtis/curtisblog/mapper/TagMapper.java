package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 博客标签的持久层接口
 */
@Mapper
public interface TagMapper {

    /**
     * 保存标签
     */
    @Insert("insert into t_tag(id,name) values(#{id},#{name})")
    void saveTag(Tag tag);

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @Select("select * from t_tag where id = #{id}")
    Tag findTagById(Long id);

    @Select("select * from t_tag where name = #{name}")
    Tag findTagByName(String name);

    /**
     * 查询所有标签
     * @return
     */
    @Select("select * from t_tag")
    List<Tag> listAll() ;

    /**
     * 删除标签
     * @param tag
     */
    @Update("update t_tag set name = #{name} where id = #{id}")
    void updateTag(Tag tag);

    /**
     * 根据id删除标签
     * @param id
     */
    @Delete("delete from t_tag where id = #{id}")
    void deleteTagById(long id);

}
