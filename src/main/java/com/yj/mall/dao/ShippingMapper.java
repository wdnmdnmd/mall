package com.yj.mall.dao;

import com.yj.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByIdAndUid(@Param("uid")Integer uid,@Param("shippingId") Integer shippingId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    List<Shipping> selectByUid(Integer uid);


    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}