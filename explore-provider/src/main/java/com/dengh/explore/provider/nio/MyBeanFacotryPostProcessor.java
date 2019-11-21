package com.dengh.explore.provider.nio;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author dengH
 * @title: MyBeanFacotryPostProcessor
 * @description: TODO
 * @date 2019/7/18 10:43
 */
public class MyBeanFacotryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("hello bean factory");
    }
}
