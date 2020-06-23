package com.dengh.explore.web.controller;

import com.alibaba.fastjson.JSONObject;
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

    @RequestMapping("/test123")
    @ResponseBody
    public LoginUser test123(@RequestBody LoginUser user){
        System.out.println("test123" + JSONObject.toJSONString(user));
        //LoginUser user = new LoginUser();
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
