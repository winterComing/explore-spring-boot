package com.dengh.explore.web.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

/**
 * @author dengH
 * @title: ServletConfiguration
 * @description: TODO
 * @date 2019/9/10 11:12
 *
 * 通过ServletRegistrationBean添加servlet
 * servlet请求会首先经过servletDemo，然后才经过dispatcherServlet，原因：首先是精确匹配
 */
@Configuration
public class ServletConfiguration {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new ServletDemo());
        servletRegistrationBean.addUrlMappings("/servletDemo");
        servletRegistrationBean.setName("servletDemo");
        return servletRegistrationBean;
    }
}
