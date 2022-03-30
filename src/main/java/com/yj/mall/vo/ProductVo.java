package com.yj.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * create by 86136
 * 2022/3/28 14:14
 */
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;

}
