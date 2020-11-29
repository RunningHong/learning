package com.hong.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author hongzh.zhang on 2020/11/29
 * 使用分区器的生产者
 */
public class ProducerPartition {
    public static void main(String[] args) {

        // 1、创建kafka生产者的配置信息
        Properties prop = new Properties();

        // kafka集群，broker-list
        // 指定连接的kafka集群
        // prop.put("bootstrap.servers", "node01:9092");
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node01:9092");

        // ack应答级别
        // prop.put("acks", "all");
        prop.put(ProducerConfig.ACKS_CONFIG, "all");

        // key,value序列化类
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 添加分区器
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.hong.kafka.partitioner.MyPartitioner");

        // 创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);

        // cli创建主题：
        // bin/kafka-topics.sh --create --zookeeper node02:2181 --topic hong_1 --partitions 2 --replication-factor 2
        // cli启动消费者：
        // bin/kafka-console-consumer.sh --bootstrap-server node01:9092 --topic hong_1 --from-beginning

        // 循环发送消息
        for (int i = 0; i < 15; i++) {
            // 消息对象
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("hong_1", "message:"+i);
            // 发送数据
            producer.send(producerRecord, (recordMetadata, e) -> {
                int partition = recordMetadata.partition();
                long offset = recordMetadata.offset();
                System.out.println("partition：" + partition + " offset: " + offset);
            });
        }

        // 关闭资源
        producer.close();
    }
}
