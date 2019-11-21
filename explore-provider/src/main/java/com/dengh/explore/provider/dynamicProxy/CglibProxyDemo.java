package com.dengh.explore.provider.dynamicProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author dengH
 * @title: CglibProxyDemo
 * @description: cglib代理示例
 * @date 2019/7/31 11:00
 *
 * 代理类通过Enhancer enhancer = new Enhancer();enhancer.setSuperclass(Doctor.class);enhancer.setCallback(new DoctorProxyMethodInterceptor());
 *       create(new Class[]{ }, new Object[]{ })来创建
 *     superClass：指定代理的类
 *     callback：传入MethodInterceptor的实现类
 *     create(): 创建代理类，并指定构造参数；
 * cglib代理是通过子类来代理的；
 */
public class CglibProxyDemo {

    static class Doctor{
        private String name;

        public Doctor(String name) {
            this.name = name;
        }

        String medicalAdvice(String appearance){
            System.out.println("a doctor " + this.name + " give a medical advice depends on appearance : " + appearance);
            return "a doctor " + this.name + " give a medical advice depends on appearance: " + appearance;
        }
    }

    static class DoctorProxyMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            methodProxy.invokeSuper(o, objects);
            return "a proxy doctor give a medical advice depends on appearance: " + objects[0];
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Doctor.class);
        enhancer.setCallback(new DoctorProxyMethodInterceptor());

        Doctor doctorProxy = (Doctor) enhancer.create(new Class[]{String.class}, new Object[]{"linda"});

        String gan_mao = doctorProxy.medicalAdvice("gan mao");
        System.out.println(gan_mao);
    }

}
