package com.hong.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author hongzh.zhang on 2020/11/29
 * 时间拦截器，在消息前加时间
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 取出数据
        String oldValue = producerRecord.value();

        // 新值
        String newValue = System.currentTimeMillis() + "," + oldValue;

        // 创建新对象&&赋值&&返回
        return new ProducerRecord<>(producerRecord.topic(),
                producerRecord.partition(),
                producerRecord.key(),
                newValue );
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }


}
