package com.hong.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * @author hongzh.zhang on 2020/08/02
 * 反射内部类
 * 通过反射的方式构造内部类实例调用方法print
 */
public class ReflectInnerClass {

    public static void main(String[] args) {
        invokeInnerClassPrintMethod();
    }


    /**
     * 普通内部类
     * @author hongzh.zhang on 2020/8/2
     */
    private class InnerNormalClassA {
        private void print(String mes) {
            System.out.println("InnerNormalClassA print: " + mes);
        }
    }

    /**
     * 静态内部类
     * @author hongzh.zhang on 2020/8/2
     */
    private static class InnerStaticClassB {
        private void print(String mes) {
            System.out.println("InnerStaticClassB print: " + mes);
        }
    }


    /**
     * 声明一个匿名内部类
     * @author hongzh.zhang on 2020/8/2
     */
    private Runnable anonymousField = new Runnable() {
        @Override
        public void run() {
            System.out.println("匿名内部类的run方法");
        }
    };


    /**
     * 调用内部类的print方法
     * @author hongzh.zhang on 2020/8/2
     */
    public static void invokeInnerClassPrintMethod() {
        // 获取外部类的类型信息
        Class clazz = ReflectInnerClass.class;
        try {
            // 得到外部类实例
            Object outerClass=clazz.newInstance();

            // 得到声明的类信息
            Class[] declaredClasses = clazz.getDeclaredClasses();
            for (Class cls : declaredClasses) {
                // 获取类修饰符
                String modifier = Modifier.toString(cls.getModifiers());

                if (modifier.contains("static")) { // 构造静态内部类
                    // 获取静态内部类实例(如果是private构造器需要使用getDeclaredConstructor，
                    // 并通过innerStatic.setAccessible(true)设置访问权限)
                    Constructor innerStatic = cls.getDeclaredConstructor();
                    innerStatic.setAccessible(true);

                    // 得到打印方法（如果方法是private的则需要使用getDe`claredMethod，
                    // 再通过printMethod.setAccessible(true);设置访问权限）
                    Method printMethod = cls.getDeclaredMethod("print", String.class);
                    printMethod.setAccessible(true);

                    // 调用方法进行打印
                    printMethod.invoke(innerStatic.newInstance(), "I am inner static class");
                } else { // 构造非静态内部类
                    // 注意：这里是通过cls.getDeclaredConstructor(clazz);来获得实例的
                    // 非静态内部类的实例创建需要依赖外部类，所以这里把外部类的类型信息传入进行构造
                    Constructor innerNormal = cls.getDeclaredConstructor(clazz);
                    innerNormal.setAccessible(true);

                    Method printMethod = cls.getDeclaredMethod("print", String.class);
                    printMethod.setAccessible(true);

                    // 注意：这里方法调用的时候传入的第一个参数问innerNormal.newInstance(outterClassInstance)
                    // 非静态内部类的实例创建需要依赖外部类，所以这里把外部类的类型信息传入进行构造
                    printMethod.invoke(innerNormal.newInstance(outerClass), "I am inner normal class");
                }
            }

            // 匿名内部类相当于一个参数值
            Field field = clazz.getDeclaredField("anonymousField");
            field.setAccessible(true);
            Runnable anonymousField = (Runnable) field.get(outerClass);
            anonymousField.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
