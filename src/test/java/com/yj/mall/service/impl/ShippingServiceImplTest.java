package com.yj.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.yj.mall.MallApplicationTests;
import com.yj.mall.dao.ShippingMapper;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.form.ShippingForm;
import com.yj.mall.service.IShippingService;
import com.yj.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.junit.Assert.*;

/**
 * create by 86136
 * 2022/4/1 11:29
 */
@Slf4j
public class ShippingServiceImplTest extends MallApplicationTests {
    @Autowired
    private IShippingService shippingService;
    private final Integer uid=1;
    private Integer shippingId=8;
    private ShippingForm form;
    @Before
    public  void before(){
        form = new ShippingForm();
        form.setReceiverName("yj");
        form.setReceiverAddress("汊河大学");
        form.setReceiverCity("扬州");
        form.setReceiverDistrict("邗江区");
        form.setReceiverMobile("123456789");
        form.setReceiverPhone("400123");
        form.setReceiverProvince("江苏");
        form.setReceiverZip("210000");
        add();
    }

    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("add={}",responseVo);
        this.shippingId =responseVo.getData().get("shippingId");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("delete={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverCity("杭州");
        ResponseVo responseVo = shippingService.update(uid, shippingId, form);
        log.info("update={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = shippingService.list(uid, 1, 10);
        log.info("list={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}