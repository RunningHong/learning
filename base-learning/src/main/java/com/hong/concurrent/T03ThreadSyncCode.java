package com.hong.concurrent;

/**
 * @author create by hongzh.zhang on 2021-01-27
 * 继承Thread，使用同步代码块的形式保证线程安全
 * 三个线程共卖100张票
 */
public class T03ThreadSyncCode extends Thread {

    // 需要共享的数据，需要设置为static，才能在不同的类中共享
    public static int ticket=100;

    // 同步监视器，这里为了让不同的T03ThreadSyncCode对象使用同一个监视器，需要声明为static
    static Object obj = new Object();

    @Override
    public void run() {

        while (ticket>0) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (obj) { // 同步监视器为obj
//            synchronized (T03ThreadSyncCode.class) { // 类型信息只有一个所以也可以作为同步监视器
                if (ticket>0) {
                    ticket--;
                    System.out.println(Thread.currentThread().getName() + "卖出1张票，剩余票量：" + ticket);
                }

            }
        }

    }

    public static void main(String[] args) {

        Thread window1 = new T03ThreadSyncCode();
        Thread window2 = new T03ThreadSyncCode();

        window1.setName("A");
        window2.setName("B");

        window1.start();
        window2.start();
    }
}