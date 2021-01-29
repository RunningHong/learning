package com.hong.concurrent;

/**
 * @author create by hongzh.zhang on 2021-01-27
 * 继承Thread，使用同步方法的形式保证线程安全
 * 三个线程共卖100张票
 */
public class T04ThreadSyncMethod extends Thread {

    // 需要共享的数据，需要设置为static，才能在不同的类中共享
    public static int ticket=100;

    @Override
    public void run() {
        while (ticket > 0) {
            sellTickets();
        }
    }

    /**
     *
     * @author Create by hongzh.zhang on 2021/1/29
     * synchronized修饰方法时同步监视器使用的是this对象，
     * 为了保证线程安全方法需要修饰为static（即监视器使用该类的类型信息）
     */
    private static synchronized void sellTickets() {
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
        Thread window1 = new T04ThreadSyncMethod();
        Thread window2 = new T04ThreadSyncMethod();

        window1.setName("A");
        window2.setName("B");

        window1.start();
        window2.start();
    }
}