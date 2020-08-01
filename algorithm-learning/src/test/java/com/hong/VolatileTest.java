package com.hong;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hongzh.zhang on 2019/6/6
 */
@Slf4j
public class VolatileTest {

    private static volatile int num = 0;

    /**
     * 启动线程个数
     */
    private final static int THREAD_NUM = 100;

    private final static int THREAD_CYCLE_NUM = 1000;

    /**
     * volatile并发新增测试，开启指定个数的线程，对num进行新增操作
     * @author hongzh.zhang on 2019/6/6
     */
    @Test
    public void concurrentIncreaseTest() {
        Thread[] threads = new Thread[THREAD_NUM];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < THREAD_CYCLE_NUM; j++) {
                    increase();
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        log.info("最终结果：{}", num);

    }

    public static void increase() {
        num++;
    }


}
