package com.hong.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author hongzh.zhang on 2020/11/29
 * 自定义分区器
 */
public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {
        // topic对应的topic数量
        // Integer partitionNum = cluster.partitionCountForTopic(topic);

        // 可自定义逻辑，这里先返回个1
        return 1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
