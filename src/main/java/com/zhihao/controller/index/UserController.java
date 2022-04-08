package com.zhihao.controller.index;

import com.zhihao.context.UserContext;
import com.zhihao.pojo.user.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/index/user")
    public String login(LoginUser loginUser){
        System.out.println(UserContext.getUser());
        return "sss";
//        return user.getUsername();
    }
}
