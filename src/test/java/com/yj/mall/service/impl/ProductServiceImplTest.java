package com.yj.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.yj.mall.MallApplicationTests;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.service.IProductService;
import com.yj.mall.vo.ProductDetailVo;
import com.yj.mall.vo.ProductVo;
import com.yj.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * create by 86136
 * 2022/3/28 14:44
 */
public class ProductServiceImplTest extends MallApplicationTests {
    @Autowired
    private IProductService productService;
    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null,2, 2);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
    @Test
    public void detail(){
        ResponseVo<ProductDetailVo> responseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}