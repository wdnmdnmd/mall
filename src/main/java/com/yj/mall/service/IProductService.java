package com.yj.mall.service;

import com.github.pagehelper.PageInfo;
import com.yj.mall.pojo.Product;
import com.yj.mall.vo.ProductDetailVo;
import com.yj.mall.vo.ProductVo;
import com.yj.mall.vo.ResponseVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by 86136
 * 2022/3/28 14:27
 */
public interface IProductService {
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);
    ResponseVo<ProductDetailVo> detail(Integer productId);
}
