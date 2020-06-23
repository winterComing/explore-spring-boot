package com.dengh.explore.provider.nio;

import com.dengh.explore.provider.beanFactory.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.Socket;
import java.nio.ByteBuffer;


public class NioFileDemo {

    public static void main(String[] args) {

        //ThreadPoolExecutor
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
        context.getBean("auto");

    }


}
