package com.yj.mall.service.impl;

import com.yj.mall.MallApplicationTests;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.service.ICategoryService;
import com.yj.mall.vo.CategoryVo;
import com.yj.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.junit.Assert.*;

/**
 * create by 86136
 * 2022/3/28 13:50
 */
@Slf4j
public class CategoryServiceImplTest extends MallApplicationTests {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
    @Test
    public void findSubCategoryById(){
        HashSet<Integer> set = new HashSet<>();
        categoryService.findSubCategoryById(100001,set);
        log.info("set={}",set);
    }
}