package com.dengh.explore.provider.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author dengH
 * @title: JdkProxyDemo
 * @description: jdk动态代理示例
 * @date 2019/7/31 11:00
 *
 * 代理类通过Proxy.newProxyInstance(classLoader,interface,InvocationHandler)生成
 * 1. interface需要接口代理
 * 2. InvocationHandler实例化需要被代理类，任何被代理方法内部均会执行其invoke()
 *
 * 重点：代理类中某个代理方法调用另一个代理方法，是不会执行代理类的方法，而是执行基类的方法；
 *      这也是造成事务失效的一个重大原因；
 */
public class JdkProxyDemo {

    interface TeacherInterface{
        String teach(String course);
        String arrangeHomeWork();
    }

    static class Teacher implements TeacherInterface{

        @Override
        public String teach(String course) {
            System.out.println("i am real teacher, teach" + course);
            System.out.println(arrangeHomeWork());
            return "i am real teacher, teach " + course;
        }

        @Override
        public String arrangeHomeWork() {
            return "origin homework";
        }


    }

    static class TeacherProxyInvocationHandler implements InvocationHandler{

        private TeacherInterface teacherInterface;

        public TeacherProxyInvocationHandler(TeacherInterface teacherInterface) {
            this.teacherInterface = teacherInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("i am a student，proxy of the teacher");
            Object invoke = method.invoke(teacherInterface, args);
            return invoke;
        }
    }

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        TeacherInterface teacherProxy = (TeacherInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{TeacherInterface.class}, new TeacherProxyInvocationHandler(teacher));
        String english = teacherProxy.teach("English");
        System.out.println(english);
    }
}
