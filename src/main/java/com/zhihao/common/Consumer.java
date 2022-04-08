package com.zhihao.common;

import com.zhihao.pojo.user.User;
import com.zhihao.service.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Consumer {
    @Autowired
    private MailService mailService;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss");

    public Consumer() {
    }

    @JmsListener(
            destination = "zyQueue"
    )
    public void receiveMessage(User user) {
        System.out.println(user.getUsername());
        PrintStream var10000 = System.out;
        String var10001 = df.format(new Date());
        var10000.println("接收队列消息时间：" + var10001 + ", 接收到消息内容:" + user);
    }

    @JmsListener(
            destination = "email"
    )
    public void receiveMessages(User user) {
        System.out.println(user.getUsername());
        mailService.sendHtmlMail(user.getEmail(), "学习论坛-注册验证码信息", "当前验证码" + user.getCode() + ", 5分钟之后将作废");
        PrintStream var10000 = System.out;
        String var10001 = df.format(new Date());
        var10000.println("接收队列消息时间：" + var10001 + ", 接收到消息内容:" + user);
    }
}