package com.yj.mall.service;

import com.yj.mall.form.CartAddForm;
import com.yj.mall.form.CartUpdateForm;
import com.yj.mall.vo.CartVo;
import com.yj.mall.vo.ResponseVo;

/**
 * create by 86136
 * 2022/3/30 16:03
 */
public interface ICartService {
    ResponseVo<CartVo> add(Integer uid,CartAddForm form);
    ResponseVo<CartVo> list(Integer uid);
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);
    ResponseVo<CartVo> delete(Integer uid,Integer productId);
    ResponseVo<Integer> sum(Integer uid);
    ResponseVo<CartVo> selectAll(Integer uid);
    ResponseVo<CartVo> unSelectAll(Integer uid);

}
