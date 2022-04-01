package com.yj.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * create by 86136
 * 2022/3/25 18:54
 */
@Data
public class UserLoginForm {
    //    @NotBlank 用于String 判断空格
//    @NotNull
//    @NotEmpty 用于集合
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank
    private String password;
}
