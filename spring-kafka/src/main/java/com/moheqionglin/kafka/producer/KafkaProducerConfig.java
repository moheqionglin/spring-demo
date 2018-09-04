package com.moheqionglin.kafka.producer;

import com.moheqionglin.kafka.SelfConfig;
import com.moheqionglin.kafka.Serializer.Address.Address;
import com.moheqionglin.kafka.Serializer.person.Person;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.moheqionglin.kafka.producer")
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> configProps(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SelfConfig.server);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PersonSerialize.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 1);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 60000);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        return configProps;
    }

    @Bean
    public ProducerFactory<Long, Person> producerFactory() {
        return new DefaultKafkaProducerFactory<>(configProps());
    }

    @Bean
    public KafkaTemplate<Long, Person> kafkaTemplate(PersonProducerListener personProducerListener) {
        //注意这个 不要把auto flush设置成true，会非常低效率
        KafkaTemplate<Long, Person> stringStringKafkaTemplate = new KafkaTemplate<>(producerFactory());
        stringStringKafkaTemplate.setProducerListener(personProducerListener);
        return stringStringKafkaTemplate;
    }

    @Bean
    public ProducerFactory<Long, Address> producerAddressFactory() {
        return new DefaultKafkaProducerFactory<>(configProps());
    }

    @Bean
    public KafkaTemplate<Long, Address> kafkaAddressTemplate(PersonProducerListener personProducerListener) {
        //注意这个 不要把auto flush设置成true，会非常低效率
        KafkaTemplate<Long, Address> stringStringKafkaTemplate = new KafkaTemplate<>(producerAddressFactory());
        stringStringKafkaTemplate.setProducerListener(personProducerListener);
        return stringStringKafkaTemplate;
    }

}

