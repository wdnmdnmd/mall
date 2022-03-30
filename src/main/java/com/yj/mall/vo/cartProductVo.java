package com.yj.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * create by 86136
 * 2022/3/30 14:59
 */
@Data
public class cartProductVo {
    private Integer productId;
    /**
     * 购物车里的数量
     */
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;
    /**
     * 等于 数量*单价
     */
    private BigDecimal productTotalPrice;

    private Integer productStock;
    /*
    商品是否选中
     */
    private Boolean productSelected;
}
