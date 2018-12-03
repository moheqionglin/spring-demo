package com.moheqionglin.kafka.producer;

import com.moheqionglin.kafka.SelfConfig;
import com.moheqionglin.kafka.Serializer.Address.Address;
import com.moheqionglin.kafka.Serializer.person.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

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

        KafkaProducer kafkaProducer = (KafkaProducer) context.getBean("kafkaProducer");
        for(int i = 20 ; i < 30; i ++){
            Person p = new Person();
            p.setName("p-"+ i);
            p.setMalel(false);
            p.setAge(1);
            p.setBirthday(new Date());
            p.setId(Long.valueOf(i));
            p.setAddress(new Address(Long.valueOf(i), "上海", "浦东", "高航"));
            p.setOthre(20l);
            kafkaProducer.sendMessage(SelfConfig.personTopic, Long.valueOf(i), p);
        }

//        for(int i = 0; i < 10; i ++){
//            kafkaProducer.sendAddressMessage("point-topic-dev4", Long.valueOf(i), new Address(Long.valueOf(i), "上海-" + i, "浦东-" + i, "高航-" + i));
//        }



        Thread.sleep(100 * 1000);
    }
}
