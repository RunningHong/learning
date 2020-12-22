package com.hong.base;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author create by hongzh.zhang on 2020-12-22
 * 堆空间oom示例
 * 运行前设置参数(Idea中Run->Edit Configurations设置)： -Xms400m -Xmx400m
 * cmd中通过jvisualvm调出堆可视化工具
 */
public class HeapOOMTest {
    byte[] array = new byte[new Random().nextInt(1024*100)];

    public static void main(String[] args) throws Exception {
        ArrayList<HeapOOMTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeapOOMTest());
            Thread.sleep(30);
        }
    }

}
