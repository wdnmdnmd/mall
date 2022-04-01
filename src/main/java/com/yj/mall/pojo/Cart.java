package com.yj.mall.pojo;

import lombok.Data;

/**
 * create by 86136
 * 2022/3/30 16:33
 */
@Data
public class Cart {
    private Integer productId;
    /**
     * 购物车里的数量
     */
    private Integer quantity;
    /*
商品是否选中
 */
    private Boolean productSelected;

    public Cart() {

    }


    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
