package com.moheqionglin.kafka.startup.springkafka.producer;

import com.google.common.base.Charsets;
import com.moheqionglin.kafka.KafkaConfig;
import com.moheqionglin.kafka.common.Address.Address;
import com.moheqionglin.kafka.common.person.Person;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<Long, Person> kafkaTemplate;

    @Autowired
    private KafkaTemplate<Long, Address> addressKafkaTemplate;

    public void sendMessage(String topicName, Long key, Person person) {
        int partitionCnt = kafkaTemplate.partitionsFor(topicName).size();

        int partition = (int) (person.getId() % partitionCnt);

        ProducerRecord<Long, Person> record = new ProducerRecord(topicName, partition, person.getId(), person);

        record.headers().add(new RecordHeader("attachment", ("万里-附件-person" + person.getId()).getBytes(Charsets.UTF_8)));
        System.out.println("---Start send ---");
        ListenableFuture<SendResult<Long, Person>> send = kafkaTemplate.send(record);
        try {
            send.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(send.isDone());
        System.out.println("---end send ---");
    }

    public void sendAddressMessage(String topicName, Long key, Address address) {
        int partitionCnt = kafkaTemplate.partitionsFor(topicName).size();

        int partition = (int) (address.getId() % partitionCnt);
        ProducerRecord<Long, Address> record = new ProducerRecord(topicName, partition, address.getId(), address);
        record.headers().add(new RecordHeader("attachment", ("万里-附件-address" + address.getId()).getBytes(Charsets.UTF_8)));
        addressKafkaTemplate.send(record);
    }
}
