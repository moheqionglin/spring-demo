package com.moheqionglin.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-22 17:09
 */
@Configuration
@ComponentScan(basePackages = "com.moheqionglin.es")
@EnableKafka
public class EsConfig {

    @Bean
    public RestHighLevelClient client(){
        RestHighLevelClient client=new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.16.217.68",9200,"http"),
                        new HttpHost("10.16.217.51",9200,"http"),
                        new HttpHost("10.16.217.67",9200,"http")
                )
        );
        return client;
    }


    private final String kafkaBootstrapServer = "10.16.217.68:9092,10.16.217.51:9092,10.16.217.67:9092";
    @Bean
    public DefaultKafkaConsumerFactory<Long, JSONObject> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "es-group-dev");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configProps, new LongDeserializer(),
                new JsonDeserializer<>(JSONObject.class));
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, JSONObject> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, JSONObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(9);
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        return factory;
    }

}