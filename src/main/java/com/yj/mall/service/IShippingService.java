package com.yj.mall.service;

import com.github.pagehelper.PageInfo;
import com.yj.mall.form.ShippingForm;
import com.yj.mall.vo.ResponseVo;

import java.util.Map;

/**
 * create by 86136
 * 2022/4/1 11:16
 */
public interface IShippingService {
    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm form);

    ResponseVo delete(Integer uid,Integer shippingId);

    ResponseVo update(Integer uid,Integer shippingId,ShippingForm form);

    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);
}
