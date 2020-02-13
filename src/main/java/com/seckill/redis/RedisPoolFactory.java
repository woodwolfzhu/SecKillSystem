package com.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig jpConfig = new JedisPoolConfig();
        jpConfig.setMaxTotal(redisConfig.getMaxActive());
        jpConfig.setMaxIdle(10);
        jpConfig.setMaxWaitMillis(redisConfig.getMaxWait() * 1000);
        // 我这里的redis 没有设置密码

        JedisPool jp = new JedisPool("47.96.191.114",6379);
        // 下面这种方法会无法 getResources,报错: Could not get a resource from the pool
//        JedisPool jp = new JedisPool(jpConfig, redisConfig.getHost(), redisConfig.getPort(),
//                redisConfig.getTimeout());
//        System.out.println("maxidle:"+redisConfig.getMaxIdle());
//        System.out.println("host:"+redisConfig.getHost());
//        System.out.println("maxActive:"+redisConfig.getMaxActive());
//        System.out.println("maxWait:"+redisConfig.getMaxWait());
//        System.out.println("------------------------------------");
//        System.out.println(jp.getNumActive());
//        System.out.println(jp.getNumIdle());
        return jp;
    }
}
