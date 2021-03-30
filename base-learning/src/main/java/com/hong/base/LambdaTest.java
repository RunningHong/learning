package com.hong.base;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author hongzh.zhang on 2021/03/28
 */
public class LambdaTest {

    public static void main(String[] args) {
        functionTest();

        PredicateTest();

        ConsumerTest();

        supplierTest();
    }


    /**
     * 函数型接口
     * 有一个输入参数以及一个输出参数
     */
    public static void functionTest() {

        // 传入String，返回String
        Function function = new Function<String, String>() {
            @Override
            public String apply(String str) {
                return "output:" + str;
            }
        };
        // 最终输出：output:hello
        System.out.println(function.apply("hello"));


        // 改装成lambda表达式
        Function function1 = (str) -> {
            return "output:" +str;
        };
        // 最终输出：output:hello2
        System.out.println(function1.apply("hello2"));

    }

    /**
     * 断定型接口
     * 有一个输入参数，返回值只能是布尔值
     */
    public static void PredicateTest() {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.isEmpty();
            }
        };
        // 最终输出：false
        System.out.println(predicate.test("abc"));

        // 改装成lambda表达式
        Predicate<String> predicate2 = (str) -> {
            return str.isEmpty();
        };
        // 最终输出：false
        System.out.println(predicate2.test("abc"));
    }


    /**
     * 消费型接口
     * 只有输入没有返回值
     */
    public static void ConsumerTest() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str);
            }
        };
        consumer.accept("print this 1");


        Consumer<String> consumer2 = (str) -> {
            System.out.println(str);
        };
        consumer2.accept("print this 2");
    }


    /**
     * 供给型接口
     * 只有输出没有输入
     */
    public static void supplierTest() {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "this is supplier provide";
            }
        };
        System.out.println(supplier.get());


        Supplier<String> supplier1 = () -> {
            return "this is supplier provide 2";
        };
        System.out.println(supplier1.get());
    }


    @Test
    public void testStreamFilter() {
        System.out.println("d");
    }


}
