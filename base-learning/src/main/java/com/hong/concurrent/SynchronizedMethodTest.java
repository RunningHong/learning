package com.hong.concurrent;

/**
 * @author create by hongzh.zhang on 2021-01-18
 * 同步方法的形式保证线程安全
 */
public class SynchronizedMethodTest implements Runnable {
    private int value = 0;

    private synchronized void add(int addVal) {
        value = value + addVal;
    }

    @Override
    public void run() {
        for (int i = 0; i <1000; i++) {
            add(1);
        }
        System.out.println("账户余额：" + value);
    }


    public static void main(String[] args) {
        SynchronizedMethodTest synchronizedMethodTest = new SynchronizedMethodTest();
        Thread threadA = new Thread(synchronizedMethodTest);
        Thread threadB = new Thread(synchronizedMethodTest);
        threadA.start();
        threadB.start();
    }

}
