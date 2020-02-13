package com.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str,clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    // 将字符串转化为Bean对象
    private <T> T stringToBean(String str,Class<T> clazz) {
        if (str == null || str.length()<=0 || clazz==null){
            return null;
        }else if (clazz==int.class||clazz==Integer.class){
            return (T) Integer.valueOf(str);
        }else if (clazz == long.class||clazz == Long.class){
            return (T) Long.valueOf(str);
        }else if (clazz == String.class){
            return (T) str;
        }else{
            // 不能解析List类型
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    public<T> boolean set(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            jedis.set(key, str);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T  value) {
        Class<?> clazz = value.getClass();
        // 为什么需要先判断value的类型?
        // JSON.toJSONString(value)不能将基本类型转换为String?
        if (clazz == int.class || clazz==Integer.class){
            return ""+value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+value;
        }else if (clazz == String.class){
            return (String) value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
