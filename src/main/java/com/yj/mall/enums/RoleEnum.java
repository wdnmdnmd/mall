package com.yj.mall.enums;

import lombok.Getter;

/**
 * create by 86136
 * 2022/3/23 21:53
 */
@Getter
public enum RoleEnum {
    ADMIN(0),
    CUSTOMER(1),
    ;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
