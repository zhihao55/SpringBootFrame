package com.zhihao.context;

import com.zhihao.pojo.user.LoginUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:存放登录的用户信息
 * @Author: 王憨憨
 * @Date: 2022/2/25 17:07
 * @version:1.0
 */
@Slf4j
//设置当前线程保持的状态
public class UserContext {
    private static ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();

    public static void setUser(LoginUser loginUser) {
        log.info("当前线程 --- [{}] ---  cv 设置用户 {} ", Thread.currentThread().getName(), loginUser);
        userHolder.set(loginUser);
    }

    public static LoginUser getUser() {
        LoginUser  user=userHolder.get();
        log.info("当前线程 --- [{}] --- 获取用户 {} ", Thread.currentThread().getName(), user);
        return user;
    }

    public static void removeUser(){
        LoginUser  user=userHolder.get();
        log.info("已销毁当前线程 --- [{}] --- 获取用户 {} ", Thread.currentThread().getName(), user);
        userHolder.remove();
    }

}
