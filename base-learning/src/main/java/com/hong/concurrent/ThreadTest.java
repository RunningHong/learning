package com.hong.concurrent;

/**
 * @author hongzh.zhang on 2021/01/17
 *
 */
public class ThreadTest extends Thread {
    public static void main(String[] args) {
        Thread thread1 = new ThreadTest("A");
        Thread thread2 = new ThreadTest("B");

        thread1.start();
        thread2.start();
    }

    private String name;

    public ThreadTest(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行  :java  " + i);
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
