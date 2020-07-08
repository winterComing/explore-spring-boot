package com.dengh.explore.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dengH
 * @title: TController
 * @description: TODO
 * @date 2019/9/12 15:35
 *
 */
@Controller
public class ControllerDemo {

    // circuitBreaker.forceOpen
    @RequestMapping("/test123")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "fallback",
            commandProperties = {
            @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false")
            })
    public LoginUser test123(@RequestBody LoginUser user){
        //int i = 1/0;
        System.out.println("正常逻辑");
        user.setName("hao");
        return user;


    }
    public LoginUser fallback(@RequestBody LoginUser user){
        System.out.println("降级策略");
        user.setName("hao");
        return user;
    }
}
class LoginUser{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
