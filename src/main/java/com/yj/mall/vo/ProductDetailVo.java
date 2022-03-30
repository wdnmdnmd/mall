package com.yj.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * create by 86136
 * 2022/3/28 16:53
 */
@Data
public class ProductDetailVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
