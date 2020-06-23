package com.dengh.explore.provider.multiThread;

import java.util.concurrent.*;

/**
 * @author dengH
 * @title: ThreadPoolDemo
 * @description: TODO
 * @date 2019/11/7 15:47
 */
public class ThreadPoolDemo {

    //public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    //public static  ExecutorService poolExecutor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        Runnable t =  new Runnable(){
            @Override
            public void run() {
                test();
                try {
                    Thread.currentThread().sleep(100000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        };
        new Thread(t, "thread0001").start();
        new Thread(t, "thread0002").start();
    }

    public static void test(){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        for (int i = 0; i <=10; i++) {
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] b = new byte[1024*1024];
                    try {
                        Thread.currentThread().sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
    }
}
