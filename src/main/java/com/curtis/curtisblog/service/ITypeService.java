package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.TopTypes;
import com.curtis.curtisblog.entity.Type;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * 类型的业务层接口
 */
public interface ITypeService {

    /**
     * 新增类型
     * @return
     */
    void saveType(Type type);

    /**
     * 根据id查询类型
     * @param id
     * @return
     */
    Type getTypeById(Long id);

    /**
     * 根据类型名称查询类型
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 查询所有类型
     * @return
     */
    List<Type> listAllType();

    /**
     * 查询使用次数前几的博客分类
     * @param size
     * @return
     */
    List<TopTypes> listTypeTop(Integer size);

    /**
     * 分页查询类型
     * @return
     */
    PageInfo<Type> getTypePage(int pageNum, int pageSize);

    /**
     * 根据id修改类型
     * @param type
     * @return
     */
    void updateType(Type type);

    /**
     * 根据id删除类型
     * @param id
     */
    void deleteTypeById(long id);
}
