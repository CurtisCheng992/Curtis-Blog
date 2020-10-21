package com.curtis.curtisblog;

import com.curtis.curtisblog.entity.Type;
import com.curtis.curtisblog.service.ITypeService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurtisBlogApplication.class)
public class TestType {

    @Autowired
    private ITypeService typeService;

    @Test
    public void testFindAll(){
        List<Type> types = typeService.listAllType();
        for (Type type : types) {
            System.out.println(type);
        }
    }

    @Test
    public void testSaveOne(){
        Type type = new Type();
        type.setId(null);
        type.setName("TestType1");
        System.out.println("-------Before-------");
        List<Type> types = typeService.listAllType();
        for (Type type1 : types) {
            System.out.println(type1);
        }
        typeService.saveType(type);
        types = typeService.listAllType();
        System.out.println("-------After-------");
        for (Type type1 : types) {
            System.out.println(type1);
        }
    }

    @Test
    public void testFindOne(){
        Type type = typeService.getTypeById(2l);
        System.out.println(type);
    }

    @Test
    public void testDelete(){
        Type type = typeService.getTypeById(3l);
        System.out.println("Before: "+type);
        typeService.deleteTypeById(3l);
        type = typeService.getTypeById(3l);
        System.out.println("After: "+type);
    }

    @Test
    public void testUpdate(){
        Type type = typeService.getTypeById(4l);
        type.setName("updateType");
        System.out.println("------Before------");
        List<Type> types = typeService.listAllType();
        for (Type type1 : types) {
            System.out.println(type1);
        }
        typeService.updateType(type);
        types = typeService.listAllType();
        System.out.println("------After------");
        for (Type type1 : types) {
            System.out.println(type1);
        }
    }

    @Test
    public void testGetPage(){
        PageInfo<Type> typePageResult = typeService.getTypePage(1, 10);
        System.out.println(typePageResult);
        long total = typePageResult.getTotal();
        System.out.println("Total: "+total);
        int pages = typePageResult.getPages();
        System.out.println("Pages: "+pages);
        List<Type> list = typePageResult.getList();
        System.out.println("--------1st List--------");
        for (Type type : list) {
            System.out.println(type);
        }

        PageInfo<Type> typePageResult1 = typeService.getTypePage(2, 10);
        List<Type> list1 = typePageResult1.getList();
        System.out.println("--------2nd List--------");
        for (Type type : list1) {
            System.out.println(type);
        }
    }
}
