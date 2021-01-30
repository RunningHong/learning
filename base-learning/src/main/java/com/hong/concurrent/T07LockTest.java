package com.hong.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * @author hongzh.zhang on 2021/01/30
 * 解决线程安全的方式3-Lock的方式
 */
public class T07LockTest implements Runnable {

    private int ticket = 100;


    // 创建一个公平的锁
    private Lock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (ticket>0) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                lock.lock(); // 开启锁
                if (ticket>0) {
                    ticket--;
                    System.out.println(Thread.currentThread().getName() + "卖出1张票，剩余票量：" + ticket);
                }
            } finally {
                lock.unlock(); // 解锁
            }

        }
    }

    public static void main(String[] args) {
        T07LockTest t07LockTest = new T07LockTest();

        Thread window1 = new Thread(t07LockTest);
        Thread window2 = new Thread(t07LockTest);

        window1.setName("A");
        window2.setName("B");

        window1.start();
        window2.start();
    }



}
