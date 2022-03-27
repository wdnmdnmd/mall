package com.yj.mall.dao;

import com.yj.mall.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * create by 86136
 * 2022/3/17 16:10
 */

public interface CategoryMapper {
    @Select("select * from mall_category where id= #{id}")
    Category findById(@Param("id") Integer id);

    Category queryById(Integer id);
}
