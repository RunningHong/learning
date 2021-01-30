package com.hong.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hongzh.zhang on 2021/01/30
 * 使用线程池的方式创建线程
 */
public class T11ThreadPoolTest {


    public static void main(String[] args) {
        // 提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);

        // 执行指定线程的操作
        service.execute(new NumberThread());// 适合于Runnable
//        service.submit(Callable callable);// 适合于Callable

        // 关闭线程池
        service.shutdown();
    }
}

class NumberThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }
}
