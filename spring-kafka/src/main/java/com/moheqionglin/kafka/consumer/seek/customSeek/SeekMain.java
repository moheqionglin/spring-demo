package com.moheqionglin.kafka.consumer.seek.customSeek;

import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-02 17:40
 */
public class SeekMain {
    public static void main(String[] args) {
        String broker = "192.168.0.1:9092";
        String topic = "topic1";
        String group = "group1";
        int partitionCnt = 15;

        KafkaConsumer kafkaConsumer = new KafkaConsumer();
        org.apache.kafka.clients.consumer.KafkaConsumer<Long, byte[]> consumer = kafkaConsumer.createConsumer(broker, topic, group);
        List<TopicPartition> topicPartitionLists = new ArrayList<>();

        for(int i = 0 ; i < partitionCnt; i ++){
            topicPartitionLists.add(new TopicPartition(topic, i));
        }
        consumer.poll(1);
        Map<TopicPartition, Long> topicPartitionLongMap = consumer.endOffsets(topicPartitionLists);
        for(TopicPartition tp : topicPartitionLongMap.keySet()){
            consumer.seek(tp, topicPartitionLongMap.get(tp));
        }

        consumer.close();
    }
}