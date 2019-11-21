package com.dengh.explore.provider.beanFactory;

import org.springframework.stereotype.Component;

/**
 * @author dengH
 * @title: NormalBean
 * @description: TODO
 * @date 2019/9/4 10:43
 */
@Component(value = "normalBean")
public class NormalBean {

    public NormalBean() {
        System.out.println("normal bean");
    }
}
