package com.moheqionglin.kafka.startup.springkafka.producer;

import com.moheqionglin.kafka.KafkaConfig;
import com.moheqionglin.kafka.common.Address.Address;
import com.moheqionglin.kafka.common.person.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class KafkaProducerMain {

    /*
    *
    * Address 发送过了，但是一直消息没有发送到 kafka，
    * 原因：  configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
          configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
         要主动 template.flush 否则默认是cache到buffer满了以后一批发一次。
         或者你可以把bugffer设置小一点。

    *
    *
    * **/
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaProducerConfig.class);
        Random random = new Random();

        KafkaProducer kafkaProducer = (KafkaProducer) context.getBean("kafkaProducer");
        for(int i = 0 ; i < 10; i ++){
                Person p = new Person(Long.valueOf(i), "Name-"+ i, new Date(), random.nextInt(100), false, random.nextLong(),
                        new Address(Long.valueOf(i), "上海-" + i, "浦东-" + i, "高航-" + i));
                kafkaProducer.sendMessage(KafkaConfig.personTopic, Long.valueOf(i), p);
                Thread.sleep(100);
        }

//        for(int i = 0; i < 10; i ++){
//            kafkaProducer.sendAddressMessage(KafkaConfig.personTopic, Long.valueOf(i),
//                    new Address(Long.valueOf(i), "上海-" + i, "浦东-" + i, "高航-" + i));
//        }


        new CountDownLatch(1).await();
    }
}
