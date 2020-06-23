package com.dengh.explore.provider.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dengH
 * @title: JedisPoolConfig
 * @description: TODO
 * @date 2020/2/9 19:56
 */
@Configuration
public class JedisPoolConfiguration {

	private int minIdle = 3;
	private int maxIdle = 300;
	private int maxTotal = 600;
	private long maxWaitMillis = 3000L;
	private boolean testOnBorrow = true;

	private String url = "192.168.99.100";
	private int port = 6379;
	private int timeout = 2000;

    private JedisPoolConfig constructJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = constructJedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, url, port, timeout);
        return jedisPool;
    }

    @Bean
    @Order(0)
    @ConditionalOnMissingBean
    public RedisTemplate redisTemplateSingle(){

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("192.168.99.100");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setDatabase(1);

        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        builder.usePooling().poolConfig(constructJedisPoolConfig());

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, builder.build());

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean
    public RedisTemplate redisTemplateSentinel(){

        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();

        redisSentinelConfiguration.setMaster(new RedisNode("192.168.99.100", 6379));
        RedisNode sentinelNode1 = new RedisNode("192.168.99.100", 6380);
        RedisNode sentinelNode2 = new RedisNode("192.168.99.100", 6381);
        RedisNode sentinelNode3 = new RedisNode("192.168.99.100", 6382);
        redisSentinelConfiguration.addSentinel(sentinelNode1);
        redisSentinelConfiguration.addSentinel(sentinelNode2);
        redisSentinelConfiguration.addSentinel(sentinelNode3);

        redisSentinelConfiguration.setPassword("123456");
        redisSentinelConfiguration.setDatabase(0);

        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        builder.usePooling().poolConfig(constructJedisPoolConfig());

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, builder.build());

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;
    }

    @Bean
    @Order(2)
    @ConditionalOnMissingBean
    public RedisTemplate redisTemplateCluster(){

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();

        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6383));
        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6384));
        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6385));
        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6386));
        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6387));
        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6388));
        redisClusterConfiguration.addClusterNode(new RedisNode("192.168.99.100", 6389));

        redisClusterConfiguration.setPassword("123456");
        redisClusterConfiguration.setMaxRedirects(5);

        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        builder.usePooling().poolConfig(constructJedisPoolConfig());

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, builder.build());

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;
    }
}
