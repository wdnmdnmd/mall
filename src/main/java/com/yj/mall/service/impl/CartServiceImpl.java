package com.yj.mall.service.impl;

import com.google.gson.Gson;
import com.yj.mall.Form.CartAddForm;
import com.yj.mall.dao.ProductMapper;
import com.yj.mall.enums.ProductStatusEnum;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.pojo.Cart;
import com.yj.mall.pojo.Product;
import com.yj.mall.service.ICartService;
import com.yj.mall.vo.CartVo;
import com.yj.mall.vo.ResponseVo;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * create by 86136
 * 2022/3/30 16:05
 */
@Service
public class CartServiceImpl implements ICartService {
    private static final String CART_REDIS_KEY_TEMPLATE="cart_%d";
    private Gson gson=new Gson();
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //把商品加到购物车
    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm form) {
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        Integer quantity=1;
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
        //key:cart_1
        //格式化String.format()
        HashOperations<String, String , String > opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //读取出购物车中该商品数量
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart;
        if (StringUtils.isEmpty(value)){
            //没有该商品，新增
             cart = new Cart(product.getId(), quantity, form.getSelected());

        }else{
            //已经有该商品，+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity()+quantity);
        }
        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart)
                );
        return null;
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String , String > opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //TODO 需要优化，不要再for循环里执行sql 用 in解决
            //思路：将购物车商品Id提取加到集合里
        }

    }

}
