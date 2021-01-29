package com.hong.concurrent;

import static java.lang.Thread.sleep;

/**
 * @author create by hongzh.zhang on 2021-01-29
 * 实现Runnable接口，使用同步代码块的形式保证线程安全
 * 三个线程共卖100张票
 */
public class T05RunnableSyncCode implements Runnable {

    // 需要共享的数据
    public static int ticket=100;

    // 同步监视器，实现Runnable接口的形式只会有一个obj，所以也可当做同步监视器，可以不用static修饰
    Object obj = new Object();

    @Override
    public void run() {
        while (ticket>0) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (obj) { // 同步监视器为obj，实现Runnable接口的形式只会有一个obj，所以也可当做同步监视器
//            synchronized (T05RunnableSyncCode.class) { // 类型信息只有一个所以也可以作为同步监视器
//            synchronized (this) { //
                if (ticket>0) {
                    ticket--;
                    System.out.println(Thread.currentThread().getName() + "卖出1张票，剩余票量：" + ticket);
                }

            }
        }
    }


    public static void main(String[] args) {
        T05RunnableSyncCode t05RunnableSyncCode = new T05RunnableSyncCode();

        Thread window1 = new Thread(t05RunnableSyncCode);
        Thread window2 = new Thread(t05RunnableSyncCode);

        window1.setName("A");
        window2.setName("B");

        window1.start();
        window2.start();
    }
}
