package com.hong.concurrent;

import static java.lang.Thread.sleep;

/**
 * @author create by hongzh.zhang on 2021-01-27
 * 多线程初试
 * 使用实现Runnable接口的形式实现多线程
 * 各自打印0到4
 */
public class T02RunnableTest implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println( Thread.currentThread().getName() + "运行  :java  " + i);
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new T02RunnableTest());
        thread1.setName("A");
        Thread thread2 = new Thread(new T02RunnableTest());
        thread2.setName("B");
        thread1.start();
        thread2.start();
    }
}
