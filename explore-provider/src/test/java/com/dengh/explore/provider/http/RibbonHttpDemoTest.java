//package com.dengh.explore.provider.http;
//
//import okhttp3.ConnectionPool;
//import okhttp3.OkHttpClient;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.*;
//
///**
// * @author dengH
// * @title: RibbonHttpDemoTest
// * @description: TODO
// * @date 2019/12/2 16:40
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RibbonHttpDemoTest {
//
//
//
//    public static final int maxIdleConnections = 5;
//
//    public static final long keepAliveDuration = 5L;
//
//    public static final long connectionTimeout = 30L;
//
//    public static final long readTimeout = 30L;
//
//    @LoadBalanced
//    @Bean
//    public static RestTemplate constructRestTemplateOkHttp(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES));
//        builder.connectTimeout(connectionTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS);
//
//        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(builder.build()));
//        return restTemplate;
//    }
//
//    @Test
//    public void test(){
//        //Object forObject = restTemplate.getForObject("http://www.baidu.com", Object.class);
//    }
//}