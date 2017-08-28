package com.tb.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanbo on 2017/8/27.
 */
public class TestRedis {

    @Test
    public void testConnect(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("testkey","tanbo");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("testkey"));
    }

    @Test
    public void testRedis(){
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-redis/spring-redis.xml");
        final RedisTemplate<String, Object> redisTemplate = appCtx.getBean("redisTemplate",RedisTemplate.class);
        //添加一个 key
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("lp", "hello word");
        //获取 这个 key 的值
        System.out.println(value.get("lp"));
        //添加 一个 hash集合
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "lp");
        map.put("age", "26");
        hash.putAll("lpMap", map);
        //获取 map
        System.out.println(hash.entries("lpMap"));
        //添加 一个 list 列表
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush("lpList", "lp");
        list.rightPush("lpList", "26");
        //输出 list
        System.out.println(list.range("lpList", 0, 1));
        //添加 一个 set 集合
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add("lpSet", "lp");
        set.add("lpSet", "26");
        set.add("lpSet", "178cm");
        //输出 set 集合
        System.out.println(set.members("lpSet"));
        //添加有序的 set 集合
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add("lpZset", "lp", 0);
        zset.add("lpZset", "26", 1);
        zset.add("lpZset", "178cm", 2);
        //输出有序 set 集合
        System.out.println(zset.rangeByScore("lpZset", 0, 2));
    }
}
