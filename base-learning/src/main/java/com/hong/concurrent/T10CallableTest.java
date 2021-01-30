package com.hong.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author hongzh.zhang on 2021/01/30
 * 创建线程的方式3：实现Callable接口
 * Callable可以有返回值，需要通过FutureTask来承接
 * 对0到100间的偶数进行求和，返回结果
 */
public class T10CallableTest implements Callable {
    @Override
    public Object call() {
        int sum=0;
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        T10CallableTest t10CallableTest = new T10CallableTest();

        // 使用FutureTask获取线程的返回值
        FutureTask futureTask = new FutureTask(t10CallableTest);

        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            // get()返回值即为FutureTask的构造器参数Callable实现类中重写call()的返回值
            Object sum = futureTask.get();
            System.out.println("total sum : " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
