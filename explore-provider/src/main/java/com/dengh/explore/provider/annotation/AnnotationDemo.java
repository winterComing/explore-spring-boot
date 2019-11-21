package com.dengh.explore.provider.annotation;

/**
 * @author dengH
 * @title: AnnotationDemo
 * @description: TODO
 * @date 2019/9/18 10:30
 */
@MyAnnotation(getValue = "test")
public class AnnotationDemo {

    public static void main(String[] args) {
        MyAnnotation annotation = AnnotationDemo.class.getAnnotation(MyAnnotation.class);
        System.out.println(annotation.getValue());
    }
}
