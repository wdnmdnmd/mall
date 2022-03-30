package com.yj.mall.controller;

import com.yj.mall.Form.CartAddForm;
import com.yj.mall.vo.CartVo;
import com.yj.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * create by 86136
 * 2022/3/30 15:08
 */
@RestController
public class CartController {
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm){
        return null;
    }
}
