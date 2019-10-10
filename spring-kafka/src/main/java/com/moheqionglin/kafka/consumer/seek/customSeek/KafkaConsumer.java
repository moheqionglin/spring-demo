package com.moheqionglin.kafka.consumer.seek.customSeek;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-02 17:23
 */
public class KafkaConsumer {


    public org.apache.kafka.clients.consumer.KafkaConsumer<Long, byte[]> createConsumer(String kafkaBootstrapServer, String topic, String group) {
        DefaultKafkaConsumerFactory<Long, byte[]> factory = createConsumerFactory(kafkaBootstrapServer, group);
        org.apache.kafka.clients.consumer.KafkaConsumer<Long, byte[]> consumer = (org.apache.kafka.clients.consumer.KafkaConsumer<Long, byte[]>) factory.createConsumer();
        consumer.subscribe(Arrays.asList(topic));
        return consumer;
    }

    private DefaultKafkaConsumerFactory<Long, byte[]> createConsumerFactory(String kafkaBootstrapServer, String group) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,100);
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new LongDeserializer(), new ByteArrayDeserializer());
    }
}