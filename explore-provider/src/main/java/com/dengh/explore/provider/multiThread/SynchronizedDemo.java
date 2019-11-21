package com.dengh.explore.provider.multiThread;

import java.util.LinkedList;

/**
 * @author dengH
 * @title: SynchronizedDemo
 * @description: TODO
 * @date 2019/9/19 16:48
 *
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        MyContainerSyn<String> myContainer = new MyContainerSyn<>(10);

        Runnable produce = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    myContainer.put(Thread.currentThread().getName());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    myContainer.get();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        for (int j = 0; j < 2; j++){
            new Thread(produce).start();
        }

        for (int i = 0; i < 10; i++){
            new Thread(consumer).start();
        }
    }

    static class MyContainerSyn<T>{

        public MyContainerSyn(int size) {
            this.size = size;
        }

        public LinkedList<T> table = new LinkedList<>();
        private int size;


        public synchronized void put(T t){
            while (table.size() >= size){
                try {
                    System.out.println("put 等待");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            table.add(t);
            System.out.println(Thread.currentThread().getName() + "设置值" + Thread.currentThread().getName()  + "，当前容量:" + table.size());
            notifyAll();
        }

        public synchronized T get(){
            while (table.size() == 0){
                try {
                    System.out.println("get 等待");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = table.removeFirst();
            System.out.println(Thread.currentThread().getName() + "获取到值" + t + ",当前容量：" + table.size());
            notifyAll();
            return t;
        }
    }

}
