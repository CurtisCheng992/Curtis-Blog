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
     * 保存类型
     */
    @Insert("insert into t_type(id,name) values(#{id},#{name})")
    void saveType(Type type);

    /**
     * 根据id查询类型
     * @param id
     * @return
     */
    @Select("select * from t_type where id = #{id}")
    Type findTypeById(Long id);

    @Select("select * from t_type where name = #{name}")
    Type findTypeByName(String name);

    /**
     * 查询所有类型
     * @return
     */
    @Select("select * from t_type")
    List<Type> listAll() ;

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
