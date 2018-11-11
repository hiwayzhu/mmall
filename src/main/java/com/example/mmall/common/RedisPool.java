package com.example.mmall.common;

import com.example.mmall.util.PropertiesUtil;
import redis.clients.jedis.JedisPool;

public class RedisPool {

    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20")); //在jedispool中最大的idle状态（空闲的）的jedis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));  //在jedispool中最小的idle状态（空闲的）的jedis实例个数
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候，是否需要进行验证操作，如果赋值true，则获得的redis实例是肯定可以用的
    private static Boolean testOnReturn =Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true")); //在return一个jedis实例的时候，是否需要进行验证操作，如果赋值true，则放回jedispool的redis实例是肯定可以用的

    private static void  init(){

    }

}
