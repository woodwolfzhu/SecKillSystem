package com.seckill.controller;

import com.seckill.domain.User;
import com.seckill.redis.RedisService;
import com.seckill.result.Result;
import com.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resources;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/")
    public String home(){
        return "hello world";
    }

    @RequestMapping("/db/id")
    public User getById(){
        return userService.getById(1);
    }

    @RequestMapping("/redis/get")
    public Result<Long> redisGet(){
        Long l1 = redisService.get("key1",Long.class);
        return Result.success(l1);
    }
}
