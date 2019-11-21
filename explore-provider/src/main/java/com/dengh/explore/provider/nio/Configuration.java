package com.dengh.explore.provider.nio;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengH
 * @title: Configuration
 * @description: TODO
 * @date 2019/7/17 10:44
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean(value = "auto")
    public Map getMap(){
        return new HashMap();
    }

    @Bean
    public MyBeanFacotryPostProcessor myBeanFacotryPostProcessor(){
        return new MyBeanFacotryPostProcessor();
    }
}
