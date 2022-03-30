package com.yj.mall.service.impl;

import com.yj.mall.MallApplicationTests;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.enums.RoleEnum;
import com.yj.mall.pojo.User;
import com.yj.mall.service.IUserService;
import com.yj.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by 86136
 * 2022/3/23 21:42
 */
@Transactional
public class UserServiceImplTest extends MallApplicationTests {
    @Autowired
    private IUserService userService;
    public static final String USERNAME="jack";
    public static final String PASSWORD="123456";

    @Test
    public void register() {
        User jack = new User(USERNAME, PASSWORD, "hello.@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(jack);
    }
    @Test
    public void login(){
        register();
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}