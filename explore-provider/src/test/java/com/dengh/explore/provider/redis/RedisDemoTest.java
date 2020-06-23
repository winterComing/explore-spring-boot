package com.dengh.explore.provider.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author dengH
 * @title: RedisDemoTest
 * @description: TODO
 * @date 2020/2/9 19:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoTest {

    @Autowired
    private RedisDemo redisDemo;

    @Test
    public void testJedis(){
        redisDemo.jedis();
    }

    @Test
    public void testJedisPool(){
        redisDemo.jedisPool();
    }

    @Test
    public void testRedisTemplate(){
        redisDemo.redisTemplate();
    }

    @Test
    public void testRedisTemplatePipeline(){
        redisDemo.redisTemplatePipeline();
    }
}