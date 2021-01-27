package com.hong.concurrent;

/**
 * @author create by hongzh.zhang on 2021-01-27
 * 多线程初试
 * 使用继承Thread类的形式实现多线程
 * 各自打印0到4
 */
public class T01ThreadTest extends Thread {

    private String name;

    public T01ThreadTest(String name) {
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

    public static void main(String[] args) {
        Thread thread1 = new T01ThreadTest("A");
        Thread thread2 = new T01ThreadTest("B");

        thread1.start();
        thread2.start();
    }

}
