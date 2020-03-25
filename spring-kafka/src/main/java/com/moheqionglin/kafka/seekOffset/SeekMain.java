package com.moheqionglin.kafka.seekOffset;

import com.moheqionglin.kafka.KafkaConfig;
import com.moheqionglin.kafka.startup.samplejava.KafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-02 17:40
 */
public class SeekMain {
    public static void main(String[] args) {
        String broker = KafkaConfig.server;
        String topic = KafkaConfig.personTopic;
        String group = "group1";
        int partitionCnt = 15;

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        KafkaConsumer consumer = new KafkaConsumer(configProps);
        consumer.subscribe(Arrays.asList(topic));
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