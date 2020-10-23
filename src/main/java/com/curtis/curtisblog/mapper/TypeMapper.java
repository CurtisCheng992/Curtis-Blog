package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 博客类型的持久层接口
 */
@Mapper
public interface TypeMapper {

    /**
     * 查询所有类型
     * @return
     */
    @Select("select * from t_type")
    @Results(id = "typeMap",
            value = {
                @Result(id = true,column = "id",property = "id"),
                @Result(column = "name",property = "name"),
            })
    List<Type> listAll() ;

    /**
     * 保存类型
     */
    @Insert("insert into t_type(id,name) values(#{id},#{name})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Long.class,before = false,statement = {"select last_insert_id()"})
    void saveType(Type type);

    /**
     * 根据id查询类型
     * @param id
     * @return
     */
    @Select("select * from t_type where id = #{id}")
    @ResultMap("typeMap")
    Type findTypeById(Long id);

    /**
     * 根据类型名查询类型
     * @param name
     * @return
     */
    @Select("select * from t_type where name = #{name}")
    @ResultMap("typeMap")
    Type findTypeByName(String name);

    /**
     * 删除类型
     * @param type
     */
    @Update("update t_type set name = #{name} where id = #{id}")
    void updateType(Type type);

    /**
     * 根据id删除类型
     * @param id
     */
    @Delete("delete from t_type where id = #{id}")
    void deleteTypeById(long id);

}
