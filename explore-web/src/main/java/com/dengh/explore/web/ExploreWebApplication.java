package com.dengh.explore.web;

import com.dengh.explore.web.resource.TestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TestController.class)
public class ExploreWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreWebApplication.class, args);
    }

}
