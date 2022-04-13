package com.zhihao.controller.index.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhihao.context.UserContext;
import com.zhihao.pojo.Result;
import com.zhihao.pojo.user.LoginUser;
import com.zhihao.service.MyWebsocketServer;
import com.zhihao.util.ReturnResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Description: 聊天控制器
 * @Author: 王憨憨
 * @Date: 2022/2/25 9:16
 * @version:1.0
 */
@RestController
@RequestMapping("/v1/chat")
public class ChatController {


    /**
     * 发送信息（单个）
     *
     * @param message
     * @param id
     * @return
     */
    @PostMapping(value = "sendmsg")
    public Result sendMsg(@RequestParam(required = true) String message, @RequestParam(required = true) String id, LoginUser loginUser) {
        //接收信息
        try {
            MyWebsocketServer.SendMessage(message,id, UserContext.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //数据持久化

        //webSocket发送信息

        return ReturnResultUtils.returnSuccess("");
    }


    /**
     * 发送到群里消息
     *
     * @return
     */
    public String sendAll(@RequestParam(required = true) String message, @RequestParam(required = true) String id, LoginUser loginUser) {


        return "zzz";
    }

    /**
     * 获取全部聊天好友列表（默认全部）
     *
     * @return
     */
    public String userList() {
        return "ssss";
    }


}
