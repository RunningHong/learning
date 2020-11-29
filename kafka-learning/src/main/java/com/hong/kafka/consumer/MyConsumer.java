package com.hong.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author hongzh.zhang on 2020/11/29
 * consumer demo
 */
public class MyConsumer {
    public static void main(String[] args) {
        // 创建消费者配置信息
        Properties prop = new Properties();

        // 连接的集群
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "node01:9092");
        // 开启自动提交
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 自动提交的延时
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // key value的反序列化
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // 消费者组
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_group_1");
        // 从头开始消费(需要设置该参数为earliest && 更改消费者组名)
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);

        // 订阅主题
        consumer.subscribe(Arrays.asList("hong_1"));

        System.out.println("启动消费者");
        while (true) {
            // 获取数据
            //在100ms内等待Kafka的broker返回数据.超时参数指定poll在多久之后可以返回，不管有没有可用的数据都要返回
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));

            // 解析并打印
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                String key = consumerRecord.key();
                String value = consumerRecord.value();

                System.out.println("key: " + key + "  value: " + value);
            }
        }

    }
}
