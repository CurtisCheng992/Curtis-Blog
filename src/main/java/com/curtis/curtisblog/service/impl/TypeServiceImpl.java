package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Type;
import com.curtis.curtisblog.mapper.TypeMapper;
import com.curtis.curtisblog.service.ITypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博客类型的业务层接口实现类
 */
@Service
public class TypeServiceImpl implements ITypeService {

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 新增类型
     * @return
     */
    @Transactional
    @Override
    public void saveType(Type type) {
        typeMapper.saveType(type);
    }

    /**
     * 根据id查询类型
     * @param id
     * @return
     */
    @Override
    public Type getTypeById(Long id) {
        return typeMapper.findTypeById(id);
    }

    /**
     * 根据类型名称查询类型
     * @param name
     * @return
     */
    @Override
    public Type getTypeByName(String name){
        return typeMapper.findTypeByName(name);
    }

    /**
     * 查询所有类型
     * @return
     */
    @Override
    public List<Type> listAllType(){
        return typeMapper.listAll();
    }

    /**
     * 分页查询类型
     * @return
     */
    @Override
    public PageInfo<Type> getTypePage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> types = typeMapper.listAll();
        PageInfo<Type> typePageInfo = new PageInfo<>(types);
        return typePageInfo;
    }

    /**
     * 根据id修改类型
     * @param type
     * @return
     */
    @Transactional
    @Override
    public void updateType(Type type) {
        typeMapper.updateType(type);
    }

    /**
     * 根据id删除类型
     * @param id
     */
    @Transactional
    @Override
    public void deleteTypeById(long id) {
        typeMapper.deleteTypeById(id);
    }
}
