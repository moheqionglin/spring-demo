package com.moheqionglin.kafka.startup.samplejava;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : KafkaConsumerFactory
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-20 16:24
 */
public class KafkaConsumerFactory {
    public KafkaConsumer<Long, String> createConsumer(Map<String, String> params) {
        if (params == null){
            params = new HashMap<>();
        }

        DefaultKafkaConsumerFactory<Long, String> factory = createConsumerFactory(params);
        KafkaConsumer<Long, String> consumer = (KafkaConsumer<Long, String>) factory.createConsumer();
        consumer.subscribe(Arrays.asList("wanli-test"));
        return consumer;
    }

    private DefaultKafkaConsumerFactory<Long, String> createConsumerFactory(Map<String, String> params) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "person-consumer-g");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new LongDeserializer(), new StringDeserializer());
    }

}