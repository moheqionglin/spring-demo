package com.moheqionglin.kafka.consumer.commonConsumer;

import com.moheqionglin.kafka.Serializer.person.Person;
import com.moheqionglin.kafka.consumer.seek.KafkaSeekConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;

/**
 * Kafka 消费一个新topic，或者 用一个新的group消费一个老的topic的话， 默认是从最近的一条消息（也就是，consumer脸上kafka见到的一条消息开始消费。）
 *
 * 后面， 如果消费以后， 冲洗程序，那么就会从上一次程序停止的那个地方开始消费。
 *
 *
 ***********************************************************
 * Kafka的 consumer offset会定位到 最近一次ACk的那个地方
 ***********************************************************
 * partition-0： 300 303 306 309
 * partition-1： 301 304 307
 * partition-2： 302 305 308
 * 
 * if(value1.getId() == 306 || value1.getId() == 304 || value1.getId() == 305){
 *     acknowledgment.acknowledge();
 * }
 * 我们只ACK 306， 304， 305 那么不管是否ACK了303，301，302那么offset都会重置到 306，307，308，程序重启以后再消费
 * 会从这以后开始消费。
 *
 ***********************************************************
 ***********************************************************
 *  */
public class KafkaConsumerMain {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConsumerConfig.class);


        KafkaListenerEndpointRegistry registry = context.getBean(KafkaListenerEndpointRegistry.class);


//        Thread.sleep(10000);
        MessageListenerContainer listenerContainer = registry.getListenerContainer("wanli-local-point-cg-22");
        listenerContainer.start();
        listenerContainer.stop();

    }
}
