package com.yj.mall.exception;

import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * create by 86136
 * 2022/3/25 20:07
 */
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody//返回json格式加这个注解
    public ResponseVo handle(RuntimeException exception){
        return ResponseVo.error(ResponseEnum.ERROR,exception.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle(){
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
/*
统一异常处理，很有用
 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public  ResponseVo notValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return ResponseVo.error(ResponseEnum.PARAM_ERROR,
                bindingResult.getFieldError().getField()+" "+
                bindingResult.getFieldError().getDefaultMessage()
                );
    }
}
