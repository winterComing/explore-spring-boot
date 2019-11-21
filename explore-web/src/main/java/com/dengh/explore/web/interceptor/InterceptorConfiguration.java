package com.dengh.explore.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author dengH
 * @title: InterceptorConfiguration
 * @description: TODO
 * @date 2019/9/12 15:04
 *
 * 通过WebMvcConfigurer添加Interceptor，返回的interceptorRegistration可以用来添加和排除拦截路径
 * 并且WebMvcConfigurer可以用来添加静态资源映射和跳转
 *
 */
@Configuration
public class InterceptorConfiguration {

    @Bean
    public WebMvcConfigurer myConfiguration(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new InterceptorDemo()).addPathPatterns("/**");
                //interceptorRegistration.addPathPatterns("/servletDemo");
            }

            /*// 添加静态资源映射
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/testStatic/**").addResourceLocations("classpath:/testStatic/");
            }
            // 添加页面跳转，省略写一个toLogin的controller方法，转发请求到index的view
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/toLogin").setViewName("index");
            }*/
        };
    }
}
