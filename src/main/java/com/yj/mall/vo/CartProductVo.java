package com.yj.mall.vo;

import com.yj.mall.pojo.Cart;
import com.yj.mall.pojo.Product;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create by 86136
 * 2022/3/30 14:59
 */
@Data
public class CartProductVo {
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

    public void copy(Product product){
        this.productId=product.getId();
        this.productName=product.getName();
        this.productSubtitle=product.getSubtitle();
        this.productMainImage=product.getMainImage();
        this.productPrice=product.getPrice();
        this.productStatus=product.getStatus();
        this.productStock=product.getStock();
    }
    public void copy(Cart cart){
        this.quantity=cart.getQuantity();
        this.productSelected=cart.getProductSelected();
        this.productTotalPrice=this.productPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
