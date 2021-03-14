package com.hong.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * @author hongzh.zhang on 2021/03/14
 * 注解测试，自定义注解
 */
public class AnnotationTest {

    @MyAnnotation
    public void test1() {
        System.out.println("this is test1()");
    }

    @MyAnnotation(friend = {"tom", "jerry"})
    public void test2() {
        System.out.println("this is test2()");
    }

    /**
     * 打印注解的值
     */
    public static void printAnnotationValues(Method method) {
        // 判断方法是否包含MyAnnotation注解
        if (method.isAnnotationPresent(MyAnnotation.class)) {
            // 得到注解实例
            MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
            // 获取注解属性的值
            String[] values = annotation.friend();
            for (String value : values) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) throws Exception {

        Class<AnnotationTest> clazz = AnnotationTest.class;
        AnnotationTest annotationTest = clazz.newInstance();


        Method test1Method = clazz.getMethod("test1");
        test1Method.invoke(annotationTest); // 调用test1()
        printAnnotationValues(test1Method); // friend1 friend2


        Method test2Method = clazz.getMethod("test2");
        test2Method.invoke(annotationTest); // 调用test2()
        printAnnotationValues(test2Method); // tom jerry
    }
}


/**
 * 自定义注解
 */
@Retention(RetentionPolicy.RUNTIME) // 表示该注解在运行时有效
@Target(ElementType.METHOD) // 表示该注解可以作用于方法上
@interface MyAnnotation {
    String[] friend() default {"friend1", "friend2"};
}