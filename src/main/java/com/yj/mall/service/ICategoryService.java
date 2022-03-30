package com.yj.mall.service;

import com.yj.mall.vo.CategoryVo;
import com.yj.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * create by 86136
 * 2022/3/27 16:52
 */
public interface ICategoryService {
    ResponseVo<List<CategoryVo>> selectAll();
    void findSubCategoryById(Integer id, Set<Integer> resultSet);
}
