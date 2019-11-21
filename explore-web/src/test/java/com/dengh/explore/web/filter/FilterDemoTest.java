package com.dengh.explore.web.filter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author dengH
 * @title: FilterDemoTest
 * @description: TODO
 * @date 2019/9/10 11:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilterDemoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void servletOrFilterDemo() throws Exception {

        HttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8090/servletDemo");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        httpGet.setConfig(requestConfig);

        HttpResponse execute = httpClient.execute(httpGet);

        HttpEntity entity = execute.getEntity();

        String result = EntityUtils.toString(entity, "utf-8");

        System.out.println(result);

        System.out.println(result.trim().equalsIgnoreCase("hello servlet demo".trim()));
    }
}