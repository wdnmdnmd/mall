package com.yj.mall.Form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * create by 86136
 * 2022/3/30 15:06
 * 添加商品
 */
@Data
public class CartAddForm {
    @NotNull
    private Integer productId;
    private Boolean selected=true;
}
