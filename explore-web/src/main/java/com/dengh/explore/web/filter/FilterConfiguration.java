package com.dengh.explore.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dengH
 * @title: FilterConfiguration
 * @description: TODO
 * @date 2019/9/10 11:00
 *
 * 通过FilterRegistrationBean添加filter
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new FilterDemo());
        registrationBean.addUrlPatterns("/servletDemo");
        registrationBean.setOrder(1);
        return  registrationBean;
    }
}
