package com.moheqionglin.kafka.startup.springkafka.consumer;

import com.google.common.base.Charsets;
import com.moheqionglin.kafka.KafkaConfig;
import com.moheqionglin.kafka.common.person.Person;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Component
public class PersonListener {

    @PostConstruct
    public void init(){
        System.out.println("11111");
    }
    @KafkaListener(id = "person-consumer-g",  topics = {KafkaConfig.personTopic},
            containerFactory = "addressKafkaListenerContainerFactory")
    public void listenBytes1(ConsumerRecord<Long, Person> record,
//                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
//                       @Header("attachment") String attachment,
                       Acknowledgment acknowledgment) {
        try {
            String topic = record.topic();
            Object key1 = record.key();
            String key = key1 == null ? "null" : key1.toString();
            Person value = record.value();
            String attachment = "";
            Iterator<org.apache.kafka.common.header.Header> att = record.headers().headers("attachment").iterator();
            if (att.hasNext()) {
                attachment = new String(att.next().value(), Charsets.UTF_8);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("消费成功：Person [topic = " + topic + ", partition = " + partition + ", offset = " + offset + ", offsets = " + offset + ", att = " + attachment + "key = "+ key +", value = " + value + "]");
            acknowledgment.acknowledge();
        }catch (Exception e){
            Person value1 = record.value();
            System.out.println("ERROR " + value1.getId() + " , " + record.partition() + ", "+ e.getMessage());
//            0- 300 303 306 309
//            1- 301 304 307
//            2- 302 305 308
            if(value1.getId() == 306 || value1.getId() == 304 || value1.getId() == 305){
                acknowledgment.acknowledge();
            }
        }
    }

}
