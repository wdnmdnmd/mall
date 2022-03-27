package com.yj.mall;

import com.yj.mall.consts.MallConst;
import com.yj.mall.enums.ResponseEnum;
import com.yj.mall.exception.UserLoginException;
import com.yj.mall.pojo.User;
import com.yj.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * create by 86136
 * 2022/3/26 22:16
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true表示继续流程，false表示中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User)request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (user==null){
            log.info("user=null");
            throw new UserLoginException();
//            return false;
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }

        return true;
    }
}
