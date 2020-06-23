//package com.dengh.explore.provider.http;
//
//import com.netflix.loadbalancer.*;
//import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
//import com.netflix.loadbalancer.reactive.ServerOperation;
//import okhttp3.ConnectionPool;
//import okhttp3.OkHttpClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
//import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//import rx.Observable;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author dengH
// * @title: RibbonHttpDemo
// * @description: TODO
// * @date 2019/12/2 11:02
// */
//public class RibbonHttpDemo {
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
//    public static RestTemplate constructRestTemplateOkHttp(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES));
//        builder.connectTimeout(connectionTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS);
//
//        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(builder.build()));
//        return restTemplate;
//    }
//
//
//
//}
