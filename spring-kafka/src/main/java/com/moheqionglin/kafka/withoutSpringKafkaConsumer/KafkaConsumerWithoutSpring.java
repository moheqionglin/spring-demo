package com.moheqionglin.kafka.withoutSpringKafkaConsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-04-17 11:03
 */
public class KafkaConsumerWithoutSpring {


    public static void main(String[] args) throws InterruptedException {
        KafkaConsumerWithoutSpring ks = new KafkaConsumerWithoutSpring();
        KafkaConsumer<Long, String> consumer = ks.createConsumer(null);
        while (true){
            ConsumerRecords<Long, String> records = consumer.poll(1000);

            for (ConsumerRecord<Long, String> record : records) {
                System.out.println("p = " + record.partition() + ", offset = " + record.offset() + ", v=" + record.value());
            }
            Thread.sleep(1000);
        }



    }

    private KafkaConsumer<Long, String> createConsumer(Map<String, String> params) {
        if (params == null){
            params = new HashMap<>();
        }

        DefaultKafkaConsumerFactory<Long, String> factory = createConsumerFactory(params);
        ConcurrentKafkaListenerContainerFactory<Long, String> longStringConcurrentKafkaListenerContainerFactory = kafkaListenerContainerFactory(factory);
        KafkaConsumer<Long, String> consumer = (KafkaConsumer<Long, String>) factory.createConsumer();

        consumer.subscribe(Arrays.asList("wanli-test"));
        return consumer;
    }

    private DefaultKafkaConsumerFactory<Long, String> createConsumerFactory(Map<String, String> params) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.2:9092,127.0.0.3:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group-wanli");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new LongDeserializer(), new StringDeserializer());
    }

    public ConcurrentKafkaListenerContainerFactory<Long, String> kafkaListenerContainerFactory(DefaultKafkaConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(10);
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        return factory;
    }
}