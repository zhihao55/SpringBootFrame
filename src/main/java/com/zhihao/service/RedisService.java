package com.zhihao.service;

import com.alibaba.fastjson.JSONObject;
import com.zhihao.pojo.user.User;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(
            required = false
    )
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    public boolean setUser(User user, String Token) {
        ValueOperations ops = this.stringRedisTemplate.opsForValue();
        ops.set(Token, user, 86400L, TimeUnit.SECONDS);
        return true;
    }

    public User getUser(String name) {
        ValueOperations ops = this.stringRedisTemplate.opsForValue();
        return (User) ops.get(name);
    }

    public boolean setUserBystringRedisTemplate(User user, String Token) {
        ValueOperations ops = this.stringRedisTemplate.opsForValue();
        ops.set(Token, JSONObject.toJSON(user).toString(), 18000L, TimeUnit.SECONDS);
        return true;
    }

    public boolean setKeyValue(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value, 300L, TimeUnit.SECONDS);
        return true;
    }

    public Object getKeyValue(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    public String getUserBystringRedisTemplate(String Token) {
        ValueOperations ops = this.stringRedisTemplate.opsForValue();
//        try {
//
//        }catch ()
//        System.out.println(ops.get(Token).toString());
        return JSONObject.toJSON(ops.get(Token)).toString();
    }

    public boolean isToken(String Token) {
        ValueOperations ops = this.stringRedisTemplate.opsForValue();
        return this.stringRedisTemplate.hasKey(Token);
    }

    public boolean deleteBystringRedisTemplate(String Token) {
        return this.stringRedisTemplate.delete(Token);
    }
}