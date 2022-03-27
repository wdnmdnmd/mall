package com.yj.mall.service;

import com.yj.mall.pojo.User;
import com.yj.mall.vo.ResponseVo;

/**
 * create by 86136
 * 2022/3/23 21:18
 */

public interface IUserService {
    /**
     * 注册
      */
    public ResponseVo register(User user);
    /**
     * 登录
     */
    public ResponseVo<User> login(String username,String password);
}
