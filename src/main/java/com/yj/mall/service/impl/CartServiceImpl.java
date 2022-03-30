package com.yj.mall.service.impl;

import com.yj.mall.Form.CartAddForm;
import com.yj.mall.dao.ProductMapper;
import com.yj.mall.enums.ProductStatusEnum;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.pojo.Product;
import com.yj.mall.service.ICartService;
import com.yj.mall.vo.CartVo;
import com.yj.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * create by 86136
 * 2022/3/30 16:05
 */
public class CartServiceImpl implements ICartService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //把商品加到购物车
    @Override
    public ResponseVo<CartVo> add(CartAddForm form) {
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //判断商品是否存在
        if (product==null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        //库存是否充足
        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }
        //写入redis

    }
}
