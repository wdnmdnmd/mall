package com.yj.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yj.mall.dao.ProductMapper;
import com.yj.mall.enums.ProductStatusEnum;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.pojo.Product;
import com.yj.mall.service.ICategoryService;
import com.yj.mall.service.IProductService;
import com.yj.mall.vo.ProductDetailVo;
import com.yj.mall.vo.ProductVo;
import com.yj.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by 86136
 * 2022/3/28 14:31
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId,Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<Integer>();
        if (categoryId!=null){
            categoryService.findSubCategoryById(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList =productList.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())||
                product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        //库存信息敏感避免返回真实值
        productDetailVo.setStock(productDetailVo.getStock()>100?100:product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
