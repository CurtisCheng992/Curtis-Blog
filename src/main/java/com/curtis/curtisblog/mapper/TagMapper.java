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
     * 查询所有标签
     * @return
     */
    @Select("select * from t_tag")
    @Results(id = "tagMap",
            value = {
                @Result(id = true,column = "id",property = "id"),
                @Result(column = "name",property = "name")
            })
    List<Tag> listAll() ;

    /**
     * 保存标签
     */
    @Insert("insert into t_tag(id,name) values(#{id},#{name})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Long.class,statement = "select last_insert_id()",before = false)
    void saveTag(Tag tag);

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @Select("select * from t_tag where id = #{id}")
    @ResultMap("tagMap")
    Tag findTagById(Long id);

    /**
     * 根据标签名查询标签
     * @param name
     * @return
     */
    @Select("select * from t_tag where name = #{name}")
    @ResultMap("tagMap")
    Tag findTagByName(String name);

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
