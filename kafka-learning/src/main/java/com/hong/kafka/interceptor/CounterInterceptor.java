package com.hong.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author hongzh.zhang on 2020/11/29
 * 计数拦截器
 * 统计成功或失败的条数
 */
public class CounterInterceptor implements ProducerInterceptor<String, String> {

    private int susNum;
    private int failNum;

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 数据不需要动，直接返回
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(recordMetadata != null) { // 成功了
            susNum++;
        }
        else {
            failNum++;
        }
    }

    @Override
    public void close() {
        // 最终输出,该close方法是被producer的close方法调用，所以producer需要调用close方法
        System.out.println("susNum:" + susNum + " failNum:" + failNum);
    }


}
