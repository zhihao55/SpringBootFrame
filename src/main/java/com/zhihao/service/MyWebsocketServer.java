package com.zhihao.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhihao.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: 王憨憨
 * @Date: 2022/2/22 17:35
 * @version:1.0
 */
@ServerEndpoint("/test")
@Component
@Slf4j
public class MyWebsocketServer {

    //声明一个静态的RedisService
    private static RedisService redisService;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//
//    private String id = "";

    //自动注入当set方法里面
    @Autowired
    public void setRedisService(RedisService redisService) {
        MyWebsocketServer.redisService = redisService;
    }

    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    //    private static Logger log = LoggerFactory.getLogger(MyWebsocketServer.class);
//      存放所有在线的客户端
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

    @PostConstruct
    public void init() {
        System.out.println("websocket 加载");
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
//        检验token
        Map<String, List<String>> parameterMap = session.getRequestParameterMap();
        //获取url传递过来的token
        String token1 = parameterMap.get("token").get(0);
        String tokens = redisService.getUserBystringRedisTemplate(token1);
        System.out.println(tokens);
        User user = (User) JSON.parseObject(tokens, User.class);
        System.out.println(user);
        //----------------------------------------

//        this.session = session;
//        this.id = String.valueOf(user.getId());//接收到发送消息的人员编号
        log.info("有新的客户端连接了: {}", user.getId());
        SessionSet.add(session);
        clients.put(String.valueOf(user.getId()), session);
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        /*//        SendMessage(clients.get(String.valueOf(user.getId())), "连接成功");*/
    }

    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
        clients.remove(this);
        SessionSet.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message,Session session) {
//        //将字符串转为数组
//        JSONObject ss = JSONObject.parseObject(message);
//        System.out.println(ss.getString("user"));
//        Map<Object, Object> map = new HashMap<>();
//
//        System.out.println(JSONObject.parseObject(message));
        log.info("服务端收到客户端发来的消息: {}", message);
        SendMessages(session,message);
//        this.sendAll(message);
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     *
     * @param session
     * @param message
     */
    public static void SendMessages(Session session, String message) {
        try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 指定Session发送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String message, String sessionId, String userId) throws IOException {
        Session session = null;
//        for (Session s : SessionSet) {
//            if(s.getId().equals(sessionId)){
//                session = s;
//                break;
//            }
//        }
//        if(session!=null){
//            SendMessages(session, message);
//        }
//        else{
//            log.warn("没有找到你指定ID的会话：{}",sessionId);
//        }

        if (clients.get(sessionId) != null) {
            session = clients.get(sessionId);
            if (!userId.equals(sessionId)) {
                SendMessages(session, message);
            } else {
                SendMessages(session, message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
           log.info("该用户不在线");
        }

    }


//    /**
//     * 群发消息
//     *
//     * @param message 消息内容
//     */
//    private void sendAll(String message) {
//        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
//            sessionEntry.getValue().getAsyncRemote().sendText(message);
//        }
//    }


//    public static void SendMessage(Session session, String message) {
//        try {
//            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
//        } catch (IOException e) {
//            log.error("发送消息出错：{}", e.getMessage());
//            e.printStackTrace();
//        }
//    }


//    public void sendtoUser(String message,String sendUserId) throws IOException {
//        if (clients.get(sendUserId) != null) {
//            if(!id.equals(sendUserId))
//                clients.get(sendUserId).sendMessage( "用户" + id + "发来消息：" + " <br/> " + message);
//            else
//                clients.get(sendUserId).sendMessage(message);
//        } else {
//            //如果用户不在线则返回不在线信息给自己
//            sendtoUser("当前用户不在线",id);
//        }
//    }

//
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }


//    /**
//     * 指定Session发送消息
//     *
//     * @param sessionId
//     * @param message
//     * @throws IOException
//     */
//    public static void SendMessage(String message, String sessionId) throws IOException {
//        Session session = null;
//        for (Session s : SessionSet) {
//            if (s.getId().equals(sessionId)) {
//                session = s;
//                break;
//            }
//        }
//        if (session != null) {
//            SendMessage(session, message);
//        } else {
//            log.warn("没有找到你指定ID的会话：{}", sessionId);
//        }
//    }


}
