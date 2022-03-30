package com.yj.mall.service;

import com.yj.mall.Form.CartAddForm;
import com.yj.mall.vo.CartVo;
import com.yj.mall.vo.ResponseVo;

/**
 * create by 86136
 * 2022/3/30 16:03
 */
public interface ICartService {
    ResponseVo<CartVo> add(Integer uid,CartAddForm form);
    ResponseVo<CartVo> list(Integer uid);

}
