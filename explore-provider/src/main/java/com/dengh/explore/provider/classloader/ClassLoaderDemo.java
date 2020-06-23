package com.dengh.explore.provider.classloader;

/**
 * @author dengH
 * @title: ClassLoaderDemo
 * @description: TODO
 * @date 2020/1/6 11:25
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        System.out.println(
                ClassLoaderDemo.class.getClassLoader().getResource("").toString());
    }
}
