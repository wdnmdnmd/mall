package com.yj.mall.enums;

import lombok.Getter;

/**
 * create by 86136
 * 2022/3/28 16:58
 */
@Getter
public enum ProductStatusEnum {
//    1在售2下架3删除
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3),
    ;
    Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
