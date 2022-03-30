package com.yj.mall.service.impl;

import com.yj.mall.dao.UserMapper;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.enums.RoleEnum;
import com.yj.mall.pojo.User;
import com.yj.mall.service.IUserService;
import com.yj.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * create by 86136
 * 2022/3/23 21:21
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo register(User user) {
        //校验用户名不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername>0){
           return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        //邮箱不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail>0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        user.setRole(RoleEnum.CUSTOMER.getCode());
        //MD5摘要算法
        user.setPassword(DigestUtils.md5DigestAsHex(
                user.getPassword().getBytes(StandardCharsets.UTF_8)));
        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount==0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user==null){
            //用户不存在
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(
                password.getBytes(StandardCharsets.UTF_8)))){
            //密码错误
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        //返回成功
        return ResponseVo.success(user);
    }
}
