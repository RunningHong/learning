package com.hong.base;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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


















    /**
     * filter是断定型接口，接受一个输入，返回true/false，类似于sql中的where
     */
    @Test
    public void testStreamFilter() {
        List<String> list = Arrays.asList("a", "bb", "bb", "cc", "ddd");
        System.out.println("原列表：" + list.toString()); // 原列表：[a, bb, bb, cc, ddd]

        // 返回新列表
        list=list.stream()
                .filter(str -> !str.equals("bb")) // 保留不为bb的数据
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[a, cc, ddd]
    }


    /**
     * distinct去重
     */
    @Test
    public void testStreamDistinct() {
        List<String> list = Arrays.asList("a", "bb", "bb", "cc", "ddd");
        System.out.println("原列表：" + list.toString()); // 原列表：[a, bb, bb, cc, ddd]

        // 返回新列表
        list=list.stream()
                .distinct() // 按遍历值去重
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[a, bb, cc, ddd]
    }

    /**
     * limit限制条数
     */
    @Test
    public void testStreamLimit() {
        List<String> list = Arrays.asList("a", "bb", "bb", "cc", "ddd");
        System.out.println("原列表：" + list.toString()); // 原列表：[a, bb, bb, cc, ddd]

        // 返回新列表
        list=list.stream()
                .limit(2) // 只返回前两条数据
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[a, bb]
    }


    /**
     * sorted-排序
     * 被排序的数据需要可比较即Comparable
     * 可以自定义比较器
     */
    @Test
    public void testStreamSorted() {
        List<String> list = Arrays.asList("a", "bb", "bb", "cc", "ddd");
        System.out.println("原列表：" + list.toString()); // 原列表：[a, bb, bb, cc, ddd]

        // 返回新列表
        list=list.stream()
                .sorted() // 按照默认比较器比较
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[a, bb, bb, cc, ddd]

        list=list.stream()
                .sorted( // 自定义比较器
                    (s1, s2)->s2.length()-s1.length()
                )
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[ddd, bb, bb, cc, a]
    }


    /**
     * skip -  跳过n个元素后再输出
     */
    @Test
    public void testStreamSkip() {
        List<String> list = Arrays.asList("a", "bb", "bb", "cc", "ddd");
        System.out.println("原列表：" + list.toString()); // 原列表：[a, bb, bb, cc, ddd]

        // 返回新列表
        list=list.stream()
                .skip(2) // 跳过前两个元素
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[bb, cc, ddd]
    }

    /**
     * map - 对每个元素做特定的操作
     * map是函数型接口，允许一个输入一个输出
     */
    @Test
    public void testStreamMap() {
        List<String> list = Arrays.asList("a", "bb", "bb", "cc", "ddd");
        System.out.println("原列表：" + list.toString()); // 原列表：[a, bb, bb, cc, ddd]

        // 返回新列表
        list=list.stream()
                .map(str-> str.toUpperCase()) // 对遍历的结果做特定操作-转换为大写
                .collect(Collectors.toList());
        System.out.println("新列表：" + list); // 新列表：[A, BB, BB, CC, DDD]
    }


    /**
     * flatMap -  将遍历的值转成一个新的流
     */
    @Test
    public void testStreamFlatMap() {
        String[] strArray = {"Hello", "World"};

        List list1 = Arrays.asList(strArray).stream()
                .map(str -> str.split("")) // 将字符串转换为字符串数组
                .collect(Collectors.toList());
        System.out.println(list1.toString()); // [[Ljava.lang.String;@5c8da962, [Ljava.lang.String;@512ddf17]

        List list2 = Arrays.asList(strArray).stream()
                .map(str -> str.split("")) // 将字符串转换为字符串数组
                .flatMap(Arrays::stream) // 将前面的结果转换为新的流
                .collect(Collectors.toList());
        System.out.println(list2.toString()); // [H, e, l, l, o, W, o, r, l, d]
    }



}