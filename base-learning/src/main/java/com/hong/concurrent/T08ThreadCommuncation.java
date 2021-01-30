package com.hong.concurrent;

/**
 * @author hongzh.zhang on 2021/01/30
 * 线程通信
 * 使用2个线程打印1-100，线程A,线程B交替打印。
 */
public class T08ThreadCommuncation {
    public static void main(String[] args) {
        PrintNumber printNumber = new PrintNumber();

        Thread window1 = new Thread(printNumber);
        Thread window2 = new Thread(printNumber);

        window1.setName("A");
        window2.setName("B");

        window1.start();
        window2.start();
    }
}



class PrintNumber implements Runnable {

    private int number = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (number<100) {
            synchronized (this) {
                notify(); // 获得锁后就唤醒其他线程，让其他线程等着（该线程此时获得锁，其他线程只有等到锁释放后才能拿到锁）
                if (number<100) {
                    System.out.println(Thread.currentThread().getName() + " : " + number);
                    number++;

                    try {
                        wait(); // 打印完后该线程就wait等待其他线程唤醒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}