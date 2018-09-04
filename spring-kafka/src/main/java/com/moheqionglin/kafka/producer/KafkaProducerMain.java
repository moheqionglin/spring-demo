package com.moheqionglin.kafka.producer;

import com.moheqionglin.kafka.Serializer.Address.Address;
import com.moheqionglin.kafka.Serializer.person.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class KafkaProducerMain {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaProducerConfig.class);

        KafkaProducer kafkaProducer = (KafkaProducer) context.getBean("kafkaProducer");
//        for(int i = 310 ; i < 320; i ++){
//            Person p = new Person();
//            p.setName("p-"+ i);
//            p.setMalel(false);
//            p.setAge(1);
//            p.setBirthday(new Date());
//            p.setId(Long.valueOf(i));
//            p.setAddress(new Address(Long.valueOf(i), "上海", "浦东", "高航"));
//            p.setOthre(20l);
//            kafkaProducer.sendMessage("point-topic-dev3", Long.valueOf(i), p);
//        }

        for(int i = 0; i < 10; i ++){
            kafkaProducer.sendAddressMessage("point-topic-dev4", Long.valueOf(i), new Address(Long.valueOf(i), "上海-" + i, "浦东-" + i, "高航-" + i));
        }


    }
}
