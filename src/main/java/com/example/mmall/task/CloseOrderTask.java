package com.example.mmall.task;

import com.example.mmall.common.Const;
import com.example.mmall.util.PropertiesUtil;
import com.example.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;

public class CloseOrderTask {


    public  void closeOrderTaskV2(){
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock,String.valueOf(System.currentTimeMillis()+lockTimeout));
        if(setnxResult != null&&setnxResult.intValue()==1){
            //如果返回的是1，则代表成功 ，获取锁。
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock);
        }else{
            System.out.println("没有获得分布式锁");

        }

    }

    public void closeOrderTaskV3(){
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock,String.valueOf(System.currentTimeMillis()+lockTimeout));
        if(setnxResult != null&&setnxResult.intValue()==1){
            //如果返回的是1，则代表成功 ，获取锁。
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock);
        }else{
            String lockValueStr = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock);
            if(lockValueStr != null && System.currentTimeMillis()>Long.parseLong(lockValueStr)){
                //再次用当前时间戳getset，返回给定的key的旧值，根据旧值判断，是否可以获取锁。
                //当key没有旧值时，即key不存在的时候，返回nil，->获取锁
                String getSetResult = RedisShardedPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock,String.valueOf(System.currentTimeMillis()+lockTimeout));
                if(getSetResult == null|| (getSetResult != null&&StringUtils.equals(lockValueStr,getSetResult))){
                    //真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock);
                }else{
                    System.out.println("没有获得分布式锁");
                }
            }else{
                System.out.println("没有获得分布式锁");
            }
            System.out.println("没有获得分布式锁");

        }


    }

    private void closeOrder(String lockName){
        RedisShardedPoolUtil.expire(lockName,50);//有效期50秒，防止死锁。
        int hour = Integer.parseInt(PropertiesUtil.getProperty("cloase.order.task.time.hour"),2);
        //关闭Service服务
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_lock);

    }
}
