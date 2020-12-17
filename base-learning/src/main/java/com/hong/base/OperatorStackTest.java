package com.hong.base;

/**
 * @author create by hongzh.zhang on 2020-12-17
 * 通过jclasslib查看字节码
 */
public class OperatorStackTest {
    public void test1() {
        String aa = "111";
        {
            String bb="22";
            System.out.println(bb);
        }

        int num1 = 3;
    }
}
