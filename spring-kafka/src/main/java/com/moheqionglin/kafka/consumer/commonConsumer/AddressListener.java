package com.moheqionglin.kafka.consumer.commonConsumer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.moheqionglin.kafka.Serializer.Address.Address;
import com.moheqionglin.kafka.Serializer.person.Person;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AddressListener {


    private final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "wanli-local-point-cg-2",  topics = "point-topic-dev4",
            containerFactory = "addressKafkaListenerContainerFactory")
//    @KafkaListener(id = "wanli-local-point-cg", topicPartitions =
//        {  @TopicPartition(topic = "point-topic-1",
//                partitionOffsets = {
//                    @PartitionOffset(partition = "3", initialOffset = "0"),
//                    @PartitionOffset(partition = "2", initialOffset = "0"),
//        })
//        })
    //, @Header("attachment") String attachment, @Header("ass") String ass
    public void listen(ConsumerRecord<Long, Address> record,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
                       Acknowledgment acknowledgment) {
        try {
            String topic = record.topic();
            Object key1 = record.key();
            String key = key1 == null ? "null" : key1.toString();
            Address value = record.value();
            String att = "";
            Iterator<org.apache.kafka.common.header.Header> attachment = record.headers().headers("attachment").iterator();
            if (attachment.hasNext()) {
                att = new String(attachment.next().value(), Charsets.UTF_8);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("-->Address消费者," + Thread.currentThread().getName() + " [id = " + value.getId() + ", topic = " + topic + ", partition = " + partition + ", offset = " + offset + ", offsets = " + offsets + ", att = " + att + "], key = " + key + ", value = " + value);
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            acknowledgment.acknowledge();

        }catch (Exception e){
//            Person value1 = record.value();
//            System.out.println("ERROR " + value1.getId() + " , " + record.partition() + ", "+ e.getMessage());
            //0- 300 303 306 309
            //1- 301 304 307
            //2- 302 305 308
//            if(value1.getId() == 306 || value1.getId() == 304 || value1.getId() == 305){
//                acknowledgment.acknowledge();
//            }
        }
    }



//    @KafkaListener(id = "wanli-local-point-cg-1",  topics = "point-topic-dev3")
//    @KafkaListener(id = "wanli-local-point-cg", topicPartitions =
//        {  @TopicPartition(topic = "point-topic-1",
//                partitionOffsets = {
//                    @PartitionOffset(partition = "3", initialOffset = "0"),
//                    @PartitionOffset(partition = "2", initialOffset = "0"),
//        })
//        })
    //, @Header("attachment") String attachment, @Header("ass") String ass
    public void listenBytes(ConsumerRecord<Long, Bytes> record,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
                       Acknowledgment acknowledgment) {
        try {
            String topic = record.topic();
            Object key1 = record.key();
            String key = key1 == null ? "null" : key1.toString();
            Bytes value1 = record.value();
//            String value = value1 != null ? value1.toString() : "null";
            Person person = JSON.parseObject(value1.get(), Person.class);
            String att = "";
            Iterator<org.apache.kafka.common.header.Header> attachment = record.headers().headers("attachment").iterator();
            if (attachment.hasNext()) {
                att = new String(attachment.next().value(), Charsets.UTF_8);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("-->消费者, [id = " + person.getId() + ", topic = " + topic + ", partition = " + partition + ", offset = " + offset + ", offsets = " + offsets + ", att = " + att + "], key = " + key + ", value = " + person);
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            acknowledgment.acknowledge();

        }catch (Exception e){
//            Person value1 = record.value();
//            System.out.println("ERROR " + value1.getId() + " , " + record.partition() + ", "+ e.getMessage());
            //0- 300 303 306 309
            //1- 301 304 307
            //2- 302 305 308
//            if(value1.getId() == 306 || value1.getId() == 304 || value1.getId() == 305){
//                acknowledgment.acknowledge();
//            }
        }

        acknowledgment.acknowledge();
    }

}
