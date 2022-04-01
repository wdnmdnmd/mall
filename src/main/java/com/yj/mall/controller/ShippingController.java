package com.yj.mall.controller;

import com.yj.mall.consts.MallConst;
import com.yj.mall.form.ShippingForm;
import com.yj.mall.pojo.Shipping;
import com.yj.mall.pojo.User;
import com.yj.mall.service.IShippingService;
import com.yj.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.ws.Response;

/**
 * create by 86136
 * 2022/4/1 13:37
 */
@RestController
public class ShippingController {
    @Autowired
    private IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShippingForm form,
                          HttpSession session){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(), form);
    }
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                          HttpSession session){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(), shippingId);
    }
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm form,
                             HttpSession session){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(), shippingId,form);
    }

    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                          HttpSession session){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(), pageNum,pageSize);
    }

}
