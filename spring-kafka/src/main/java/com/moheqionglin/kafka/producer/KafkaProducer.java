package com.moheqionglin.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.moheqionglin.kafka.Serializer.Address.Address;
import com.moheqionglin.kafka.Serializer.person.Person;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<Long, Person> kafkaTemplate;

    @Autowired
    private KafkaTemplate<Long, Address> addressKafkaTemplate;

    public void sendMessage(String topicName, Long key, Person person) {
        System.out.println("==>>>>>>" + kafkaTemplate.partitionsFor(topicName).size());

        int partition = (int) (person.getId() % 3);
        ProducerRecord<Long, Person> record = new ProducerRecord(topicName, partition,  person.getId(), person);
        record.headers().add(new RecordHeader("attachment", "万里-附件".getBytes(Charsets.UTF_8)));
        kafkaTemplate.send(record);
    }

    public void sendAddressMessage(String topicName, Long key, Address address) {
        try {
            System.out.println("==>>>>>>" + addressKafkaTemplate.partitionsFor(topicName).size());

            int partition = (int) (address.getId() % 3);
            ProducerRecord<Long, Address> record = new ProducerRecord(topicName, partition, address.getId(), address);
            record.headers().add(new RecordHeader("attachment", "万里-附件".getBytes(Charsets.UTF_8)));
            ListenableFuture<SendResult<Long, Address>> future = addressKafkaTemplate.send(record);
            future.addCallback(new ListenableFutureCallback<SendResult<Long, Address>>() {

                @Override
                public void onSuccess(SendResult<Long, Address> integerAddressSendResult) {

                }

                @Override
                public void onFailure(Throwable ex) {
                    ex.printStackTrace();
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
