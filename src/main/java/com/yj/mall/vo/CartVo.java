package com.yj.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * create by 86136
 * 2022/3/30 14:53
 * 购物车
 */
@Data
public class CartVo {
    private List<cartProductVo> cartProductVoList;
    private Boolean selectAll;
    //购物车所有商品总价
    private BigDecimal cartTotalPrice;
    //购物车商品总数量
    private Integer cartTotalQuantity;
}
