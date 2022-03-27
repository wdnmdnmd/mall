package com.yj.mall.controller;

import com.yj.mall.Form.UserLoginForm;
import com.yj.mall.Form.UserRegisterForm;
import com.yj.mall.consts.MallConst;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.pojo.User;
import com.yj.mall.service.IUserService;
import com.yj.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * create by 86136
 * 2022/3/25 17:09
 */
@RestController

@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("注册提交参数有误{} {}",bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }
        User user = new User();
        //对象拷贝的方法
        BeanUtils.copyProperties(userRegisterForm,user);
        //dto
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid@RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession session){
        if (bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置session
        session.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());

        return userResponseVo;
    }
    //session保存在session中,token+redis
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        log.info("/user sessionId={}",session.getId());
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success(user);
    }
    //TODO 判断登陆状态
    @PostMapping("user/logout")
    /**
     * {@link org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory}
     */
    public ResponseVo logout(HttpSession session){
        log.info("/user/logout sessionId={}",session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}
