package com.hong.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author hongzh.zhang on 2020/11/29
 * 发送消息后回调信息
 */
public class ProducerCallBack {

    public static void main(String[] args) {
        // 创建kafka生产者的配置信息
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node01:9092");
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        // key,value序列化类
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);

        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    "hong_1", "mes:" + i );

            producer.send(producerRecord, (recordMetadata, e) -> {
                if (e == null) {
                    int partition = recordMetadata.partition();
                    long offset = recordMetadata.offset();
                    System.out.println("partition：" + partition + " offset: " + offset);
                }
            });

        }

        producer.close();
    }

}
