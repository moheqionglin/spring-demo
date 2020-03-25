package com.moheqionglin.kafka.startup.springkafka.producer;

import com.moheqionglin.kafka.KafkaConfig;
import com.moheqionglin.kafka.common.Address.Address;
import com.moheqionglin.kafka.common.person.Person;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
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
@ComponentScan(basePackages = "com.moheqionglin.kafka.startup.springkafka.producer")
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> configProps(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.server);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PersonSerialize.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 1);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 102400);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 102400000);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
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
        //这个生产可以不要
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

