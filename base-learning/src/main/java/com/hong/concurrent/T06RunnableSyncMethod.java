package com.hong.concurrent;

import static java.lang.Thread.sleep;

/**
 * @author create by hongzh.zhang on 2021-01-29
 * 实现Runnable接口，使用同步方法的形式保证线程安全
 * 三个线程共卖100张票
 */
public class T06RunnableSyncMethod implements Runnable {

    // 需要共享的数据
    public static int ticket=100;

    @Override
    public void run() {
        while (ticket > 0) {
            sellTickets();
        }
    }

    /**
     * @author Create by hongzh.zhang on 2021/1/29
     * synchronized修饰方法时同步监视器使用的是this对象，
     * 这里不需要像继承Thread的形式给方法加上static，因为this对象直接就是共享的
     */
    private synchronized void sellTickets() {
        if (ticket>0) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ticket--;
            System.out.println(Thread.currentThread().getName() + "卖出1张票，剩余票量：" + ticket);
        }
    }


    public static void main(String[] args) {
        T06RunnableSyncMethod t06RunnableSyncMethod = new T06RunnableSyncMethod();

        Thread window1 = new Thread(t06RunnableSyncMethod);
        Thread window2 = new Thread(t06RunnableSyncMethod);

        window1.setName("A");
        window2.setName("B");

        window1.start();
        window2.start();
    }

}