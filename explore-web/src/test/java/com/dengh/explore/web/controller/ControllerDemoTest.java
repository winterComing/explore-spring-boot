package com.dengh.explore.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

/**
 * @author dengH
 * @title: ControllerDemoTest
 * @description: TODO
 * @date 2020/2/6 15:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerDemoTest {

    @Autowired ControllerDemo controllerDemo;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Test
    public void loginTest(){
        controllerDemo.test123(new LoginUser());
    }

    @Before
    public void before(){
        //初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test() throws Exception {
        LoginUser user = new LoginUser();
        user.setName("dengh");

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/test123")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(user).getBytes()));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }
}