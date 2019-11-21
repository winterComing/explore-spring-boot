package com.dengh.explore.provider.nio;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.InputStream;
import java.nio.ByteBuffer;


public class NioFileDemo {

    public static void main(String[] args) {

        //ThreadPoolExecutor
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
        context.getBean("auto");


    }


}
