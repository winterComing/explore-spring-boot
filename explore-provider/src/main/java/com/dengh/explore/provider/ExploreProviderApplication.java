package com.dengh.explore.provider;

import com.dengh.explore.provider.http.RestTemplateDemo;
import io.micrometer.core.instrument.MeterRegistry;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ExploreProviderApplication {

    @Autowired
    private static ApplicationContext applicationContext;

  /*  @Bean

    public static RestTemplate constructRestTemplateOkHttp(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(new ConnectionPool(RestTemplateDemo.maxIdleConnections, RestTemplateDemo.keepAliveDuration, TimeUnit.MINUTES));
        builder.connectTimeout(RestTemplateDemo.connectionTimeout, TimeUnit.SECONDS).readTimeout(RestTemplateDemo.readTimeout, TimeUnit.SECONDS);

        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(builder.build()));
        return restTemplate;
    }*/



    public static void main(String[] args) {

        SpringApplication.run(ExploreProviderApplication.class, args);

        //System.out.println(applicationContext.getBean(RestTemplate.class));
    }

}
