package com.yj.mall.controller;

import com.yj.mall.pojo.Category;
import com.yj.mall.service.ICategoryService;
import com.yj.mall.vo.CategoryVo;
import com.yj.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by 86136
 * 2022/3/27 17:17
 */
@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){
        return categoryService.selectAll();
    }
}
