package com.zhihao.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhihao.common.MyException;
import com.zhihao.context.UserContext;
import com.zhihao.pojo.user.LoginUser;
import com.zhihao.pojo.user.User;
import com.zhihao.service.RedisService;
import com.zhihao.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("x-token");
        //Bearer auth认证
        String token = request.getHeader("authorization");
//        if (){
//
//        }
        System.out.println(token);
        if (token == null) {
            throw new MyException(203, "没有权限访问", "");
        } else {
//            String[] tokens = token.split(" ");
//            if (!(tokens.length >= 2)) {
//                System.out.println("当前没有传递auth没有token");
//                throw new MyException(204, "没有权限访问", "");
//            }
//            System.out.println(tokens.length);
//            token=tokens[1];
            if (TokenUtil.VerifyJWTToken(token)) {
                String tokenInfo = redisService.getUserBystringRedisTemplate(token);
                User user = (User) JSON.parseObject(tokenInfo, User.class);
                LoginUser lu = new LoginUser();
                lu.setId(user.getId());
                lu.setUsername(user.getUsername());
                UserContext.setUser(lu);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
