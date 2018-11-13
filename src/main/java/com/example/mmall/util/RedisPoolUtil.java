package com.example.mmall.util;

import com.example.mmall.common.RedisPool;
import redis.clients.jedis.Jedis;

public class RedisPoolUtil {

    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
