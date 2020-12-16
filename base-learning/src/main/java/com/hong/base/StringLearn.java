package com.hong.base;

/**
 * @author create by hongzh.zhang on 2020-12-16
 */
public class StringLearn {
    public static void main(String[] args) {
        String str1 = "a"+"b"+"c";
        String str2 = "abc";

        // true: 编译时将"a"+"b"+"c"转换为"abc"并存储到常量池
        System.out.println(str1 == str2);


        String str3 = new String("abc");
        System.out.println(str2 == str3); // false : new了个新对象
        System.out.println(str2 == str3.intern()); // true

    }
}
