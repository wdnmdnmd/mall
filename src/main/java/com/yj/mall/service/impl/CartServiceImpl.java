package com.yj.mall.service.impl;

import com.google.gson.Gson;
import com.yj.mall.form.CartAddForm;
import com.yj.mall.dao.ProductMapper;
import com.yj.mall.enums.ProductStatusEnum;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.form.CartUpdateForm;
import com.yj.mall.pojo.Cart;
import com.yj.mall.pojo.Product;
import com.yj.mall.service.ICartService;
import com.yj.mall.vo.CartProductVo;
import com.yj.mall.vo.CartVo;
import com.yj.mall.vo.ResponseVo;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * create by 86136
 * 2022/3/30 16:05
 */
@Service
public class CartServiceImpl implements ICartService {
    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";
    private Gson gson = new Gson();
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //把商品加到购物车
    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        Integer quantity = 1;
        //判断商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        //库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //写入redis
        //key:cart_1
        //格式化String.format()
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //读取出购物车中该商品数量
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart;
        if (StringUtils.isEmpty(value)) {
            //没有该商品，新增
            cart = new Cart(product.getId(), quantity, form.getSelected());

        } else {
            //已经有该商品，+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart)
        );
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        HashSet<Integer> productIdSet = new HashSet<>();
        //返回值
        CartVo cartVo = new CartVo();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
//            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //思路：将购物车商品Id提取加到集合里
            productIdSet.add(productId);
        }
        //所有购物车里的产品
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        LinkedList<CartProductVo> cartProductVoList = new LinkedList<>();
        BigDecimal cartTotalPrice = new BigDecimal(0);
        Integer cartTotalQuantity = 0;
        boolean selectedAll = true;
        for (Product product : productList) {
            CartProductVo cartProductVo = new CartProductVo();
            //组装购物车产品类
            cartProductVo.copy(product);
            Cart cart = gson.fromJson(entries.get(String.valueOf(product.getId())), Cart.class);
            cartProductVo.copy(cart);
            cartProductVoList.add(cartProductVo);
            //如果这个商品被选中 更新购物车总价和购物车商品总数
            if (cartProductVo.getProductSelected()) {
                cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                cartTotalQuantity += cartProductVo.getQuantity();
            } else {
                selectedAll = false;
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        //购物车商品总价
        cartVo.setCartTotalPrice(cartTotalPrice);
        //购物车商品数量
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        //购物车商品全被选中
        cartVo.setSelectAll(selectedAll);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //读取出购物车中该商品数量
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //已经有该商品，修改内容
        Cart cart = gson.fromJson(value, Cart.class);
        if (form.getQuantity() != null && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setProductSelected(form.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //读取出购物车中该商品数量
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(redisKey,String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {

        Integer sum = listForCart(uid).stream().map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }
        return list(uid);
    }

    private List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(),Cart.class));
        }
        return cartList;
    }

}
