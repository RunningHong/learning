package com.hong.concurrent;

/**
 * @author create by hongzh.zhang on 2021-01-18
 * 同步代码块的方式保证线程安全
 */
public class SynchronizedCodeTest implements Runnable {

    private int value = 0;

    private void add(int addVal) {
        synchronized (this) {
            value = value + addVal;
        }
    }

    @Override
    public void run() {
        for (int i = 0; i <1000; i++) {
            add(1);
        }
        System.out.println("账户余额：" + value);
    }


    public static void main(String[] args) throws Exception {
        SynchronizedCodeTest synchronizedCodeTest = new SynchronizedCodeTest();
        Thread threadA = new Thread(synchronizedCodeTest);
        Thread threadB = new Thread(synchronizedCodeTest);
        threadA.start();
        threadB.start();

        threadA.sleep(1000);
        threadB.sleep(1000);
    }
}
