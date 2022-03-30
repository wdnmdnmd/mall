package com.yj.mall.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * create by 86136
 * 2022/3/27 16:50
 */
@Data
public class CategoryVo {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sortOrder;
    private List<CategoryVo> subCategory;
}

