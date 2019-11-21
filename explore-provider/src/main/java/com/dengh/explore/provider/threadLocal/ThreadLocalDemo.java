package com.dengh.explore.provider.threadLocal;

/**
 * @author dengH
 * @title: ThreadLocalDemo
 * @description: TODO
 * @date 2019/9/19 9:20
 */
public class ThreadLocalDemo {

    private ThreadLocal<String> name = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return "init";
        }
    };

    public static void main(String[] args) {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();

        System.out.println(threadLocalDemo.name.get());

        threadLocalDemo.name.set("main");

        threadLocalDemo.name.set("main2");

        System.out.println(threadLocalDemo.name.get());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocalDemo.name.set("run");
                System.out.println(threadLocalDemo.name.get());
            }
        });
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                System.out.println("线程1");
            }
        };



    }
}
