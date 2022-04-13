package com.zhihao.controller.index.v1;

import com.alibaba.fastjson.JSON;
import com.zhihao.pojo.Result;
import com.zhihao.pojo.user.User;
import com.zhihao.service.RedisService;
import com.zhihao.service.user.UserService;
import com.zhihao.util.ReturnResultUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Queue;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @PostMapping(
            value = {"/login"},
            produces = {"application/json"}
    )
    public Result login(@NotNull(message = "username不能为空") String username, @NotNull(message = "password不能为空") String password) {
        User user = userService.login(username, password);
        String userId = String.valueOf(user.getId());
        String token = userService.getToken(userId, user.getUsername());
        this.redisService.setUserBystringRedisTemplate(user, token);
        HashMap<Object, Object> map = new HashMap();
        map.put("token", token);
        map.put("user", user);
        return ReturnResultUtils.returnSuccess(map);
    }

    @PostMapping(
            value = {"/changetoken"},
            produces = {"application/json"}
    )
    public Result changeToken(@NotBlank(message = "token不能为空") String token) {
        boolean bool = this.redisService.isToken(token);
        if (!bool) {
            Result result = ReturnResultUtils.returnFail(400, "token已完全失效");
            return result;
        } else {
            String tokens = redisService.getUserBystringRedisTemplate(token);
            User user = (User) JSON.parseObject(tokens, User.class);
            String assetToken = userService.getToken(String.valueOf(user.getId()), user.getUsername());
            redisService.deleteBystringRedisTemplate(token);
            redisService.setUserBystringRedisTemplate(user, assetToken);
            HashMap<Object, Object> map = new HashMap();
            map.put("token", assetToken);
            map.put("user", user);
            return ReturnResultUtils.returnSuccess(map);
        }
    }

    @PostMapping(
            value = {"/register"},
            produces = {"application/json;charset=utf-8"}
    )
    public Result register(@NotNull(message = "邮箱不能为空") @Email(message = "请输入正确的邮箱") String email, @NotNull(message = "验证码必填") String code, @NotNull(message = "密码必填") String password) {
        System.out.println(this.redisService.getKeyValue(email));
        String codes = (String) this.redisService.getKeyValue(email);
        System.out.println(code);
        if (null != codes && code.equals(codes)) {
            this.userService.register(email, password);
            return ReturnResultUtils.returnFail(200, "注册成功");
        } else {
            return ReturnResultUtils.returnFail(400, "验证码错误");
        }
    }

    @PostMapping(
            value = {"/email"},
            produces = {"application/json"}
    )
    public Result mail(@Valid @NotNull(message = "邮箱必填") @Email(message = "请输入正确的邮箱") String email) {
        //生成验证码
        int code = (int) ((Math.random() * 9.0D + 1.0D) * 100000.0D);
        Queue queue = new ActiveMQQueue("email");
        User user = new User();
        user.setCode(code);
        user.setEmail(email);
        jmsMessagingTemplate.convertAndSend(queue, user);
        redisService.setKeyValue(email, String.valueOf(code));
        return ReturnResultUtils.returnSuccess("");
    }

    @GetMapping({"/test"})
    public String test() {
        Queue queue = new ActiveMQQueue("zyQueue");
        User user = new User();
        user.setId(1);
        user.setUsername("sss");
        System.out.println(user);
        jmsMessagingTemplate.convertAndSend(queue, user);
        return "sss";
    }

    /**
     * 获取全部好友
     * @return
     */
    @GetMapping({"/findall"})
    public Result findAll() {
        List<User> user = userService.findAll();
        HashMap<Object, Object> map = new HashMap();
        map.put("user", user);
        return ReturnResultUtils.returnSuccess(user);
    }

    /**
     * 用户添加书本
     * @return
     */
    @PostMapping(value = "/addbook")
    public Result addBook(@Valid @RequestBody User user){
        userService.addBooks(user);
//        System.out.println(user.getBook().getBookValue());
        return ReturnResultUtils.returnSuccess("");
    }
}
