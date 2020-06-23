package com.dengh.explore.provider.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author dengH
 * @title: RedisDemo
 * @description: TODO
 * @date 2020/2/9 19:01
 */
@Component
public class RedisDemo {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedisTemplate redisTemplate;

    public void jedis(){
        Jedis jedis = new Jedis("192.168.99.100", 6379);
        System.out.println(jedis.ping());
        jedis.set("jedisKey", "jedisValue");
        System.out.println(jedis.get("jedisKey"));
        jedis.close();
    }

    public void jedisPool(){
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());
        System.out.println(jedis.get("jedisKey"));
        jedis.close();
    }

    public void redisTemplate(){
        JSONObject jsonObject = new JSONObject();
        JSONObject remark = new JSONObject();
        remark.put("home", "mc");
        remark.put("phone", 18040);
        jsonObject.put("name", "dengh");
        jsonObject.put("age", 29);
        jsonObject.put("remark", remark);
        redisTemplate.opsForList().leftPush("user", jsonObject.toJSONString());

        redisTemplate.boundHashOps("employee").put("user", jsonObject);
        System.out.println(redisTemplate.opsForList().leftPop("user"));
        System.out.println(redisTemplate.opsForHash().get("employee", "user"));
    }

    public void redisTemplatePipeline(){
        JSONObject jsonObject = new JSONObject();
        JSONObject remark = new JSONObject();
        remark.put("home", "mc");
        remark.put("phone", 18040);
        jsonObject.put("name", "dengh");
        jsonObject.put("age", 29);
        jsonObject.put("remark", remark);

        List list = redisTemplate.executePipelined(new RedisCallback<JSONObject>() {
            @Override
            public JSONObject doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hSet("my-hash".getBytes(), "key2".getBytes(), "va2".getBytes());
                connection.lPush("my-list".getBytes(), "new1".getBytes(), "new2".getBytes());
                connection.set("newKey1".getBytes(), "newValue1".getBytes());

                connection.hGet("my-hash".getBytes(), "key2".getBytes());
                connection.lPop("my-list".getBytes());
                connection.get("newKey1".getBytes());
                return null;
            }
        });
        System.out.println(list);
    }
}
