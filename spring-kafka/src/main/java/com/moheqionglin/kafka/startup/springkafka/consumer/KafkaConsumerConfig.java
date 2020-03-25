package com.moheqionglin.kafka.startup.springkafka.consumer;

import com.moheqionglin.kafka.KafkaConfig;
import com.moheqionglin.kafka.common.Address.Address;
import com.moheqionglin.kafka.common.person.Person;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
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

@Configuration
@EnableKafka
@ComponentScan(basePackages = "com.moheqionglin.kafka.startup.springkafka.consumer")
/**
 * 注意 Listener @Bean  不要和 @Component重复了
 */
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> getStringObjectMap() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.server);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfig.consumerGroup);
        //如果设置 Auto_Commit_config = true的时候，
        // 那么不只要Listener 函数执行结束，不管是正常结束还是异常结束,都会忽略该条message
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");//latest
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PersonDeserialize.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return configProps;
    }


    //TODO
//  Caused by: org.apache.kafka.common.KafkaException: Could not instantiate class org.springframework.kafka.support.serializer.JsonDeserializer
    //https://stackoverflow.com/questions/42419526/kafkaexception-could-not-instantiate-class-jsondeserializer
    @Bean
    public DefaultKafkaConsumerFactory<Long, Person> personConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getStringObjectMap(), new LongDeserializer(), new JsonDeserializer<>(Person.class));
    }

    @Bean("personKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Person> personKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(personConsumerFactory());
        factory.setConcurrency(3);
//        factory.getContainerProperties().setPollTimeout(30000);
//        factory.setAutoStartup(false);
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        return factory;
    }


    @Bean
    public DefaultKafkaConsumerFactory<Long, Address> addressConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getStringObjectMap(), new LongDeserializer(), new JsonDeserializer<>(Address.class));
    }

    @Bean("addressKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Address> addressKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, Address> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(addressConsumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        return factory;
    }


//    @Bean
    public PersonListener personListener() {
        return new PersonListener();
    }

//    @Bean
//    public PersonSeekListener personSeekListener() {
//        return new PersonSeekListener();
//    }


//    @Bean
    public AddressListener addressListener() {
        return new AddressListener();
    }

//    @Bean
//    public Listener1 listener1(){
//        return new Listener1();
//    }
}
