package com.dengh.explore.web;

import com.dengh.explore.web.resource.TestController;
import com.dengh.explore.web.servlet.ServletDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TestController.class)
public class ExploreWebApplication {

    @Autowired
    private EmbeddedWebServerFactoryCustomizerAutoConfiguration.TomcatWebServerFactoryCustomizerConfiguration customizerConfiguration;


    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(ExploreWebApplication.class, args);
        //TomcatWebServerFactoryCustomizer bean = run.getBean(TomcatWebServerFactoryCustomizer.class);
        //System.out.println(bean.);
    }

    @Bean
    public String test(){


        return new String("abc");
    }

}
