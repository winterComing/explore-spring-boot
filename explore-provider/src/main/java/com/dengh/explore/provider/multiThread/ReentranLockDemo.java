package com.dengh.explore.provider.multiThread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dengH
 * @title: ReentranLockDemo
 * @description: TODO
 * @date 2019/9/23 16:02
 */
public class ReentranLockDemo {

    public static void main(String[] args) {
        MyContainerReen<String> myContainer = new MyContainerReen<>(10);
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

    static class MyContainerReen<T>{

        public MyContainerReen(int size) {
            this.size = size;
        }

        public LinkedList<T> table = new LinkedList<>();
        private int size;

        private Lock lock = new ReentrantLock();
        private Condition produceCondition = lock.newCondition();
        private Condition consumerCondition = lock.newCondition();

        public void put(T t){
            lock.lock();
            while (table.size() >= size){
                try {
                    System.out.println("put 等待");
                    produceCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            table.add(t);
            System.out.println(Thread.currentThread().getName() + "设置值" + Thread.currentThread().getName()  + "，当前容量:" + table.size());
            consumerCondition.signal();
            lock.unlock();
        }

        public T get(){
            lock.lock();
            while (table.size() == 0){
                try {
                    System.out.println("get 等待");
                    consumerCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = table.removeFirst();
            System.out.println(Thread.currentThread().getName() + "获取到值" + t + ",当前容量：" + table.size());
            produceCondition.signalAll();
            lock.unlock();
            return t;
        }
    }
}
