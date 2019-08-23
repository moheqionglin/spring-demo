package com.moheqionglin.kafka.consumer.commonConsumer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.moheqionglin.kafka.SelfConfig;
import com.moheqionglin.kafka.Serializer.person.Person;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PersonListener implements ConsumerSeekAware {

    private final ThreadLocal<ConsumerSeekCallback> seekCallBack = new ThreadLocal<>();

    private class FlagClass{
        public boolean flag = true;

    }
    FlagClass flagClass = new FlagClass();

    @PostConstruct
    public void init(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() ->{
            System.out.println("ping pang");
        }, 100, 10, TimeUnit.SECONDS);

        new Thread(()->{
            try {
                Thread.sleep(10* 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("状态位改变");
            flagClass.flag = false;
        }).start();
    }

    private final CountDownLatch latch1 = new CountDownLatch(1);

//    @KafkaListener(id = "wanli-local-point-cg-1",  topics = "point-topic-dev3")
//    @KafkaListener(id = "wanli-local-point-cg", topicPartitions =
//        {  @TopicPartition(topic = "point-topic-1",
//                partitionOffsets = {
//                    @PartitionOffset(partition = "3", initialOffset = "0"),
//                    @PartitionOffset(partition = "2", initialOffset = "0"),
//        })
//        })
    //, @Header("attachment") String attachment, @Header("ass") String ass
    public void listen(ConsumerRecord<Long, Object> record,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
                       Acknowledgment acknowledgment) {
        try {
            String topic = record.topic();
            Object key1 = record.key();
            String key = key1 == null ? "null" : key1.toString();
            Object value1 = record.value();
            String value = value1 != null ? value1.toString() : "null";
            Person person = JSON.parseObject(value, Person.class);
            String att = "";
            Iterator<org.apache.kafka.common.header.Header> attachment = record.headers().headers("attachment").iterator();
            if (attachment.hasNext()) {
                att = new String(attachment.next().value(), Charsets.UTF_8);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("-->消费者, [id = " + person.getId() + ", topic = " + topic + ", partition = " + partition + ", offset = " + offset + ", offsets = " + offsets + ", att = " + att + "], key = " + key + ", value = " + value);
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



    @KafkaListener(id = "wanli-local-point-cg-22",  topics = SelfConfig.personTopic, containerFactory = "kafkaListenerContainerFactory")
//    @KafkaListener(id = "wanli-local-point-cg", topicPartitions =
//        {  @TopicPartition(topic = "point-topic-1",
//                partitionOffsets = {
//                    @PartitionOffset(partition = "3", initialOffset = "0"),
//                    @PartitionOffset(partition = "2", initialOffset = "0"),
//        })
//        })
    //, @Header("attachment") String attachment, @Header("ass") String ass
    public void listenBytes(ConsumerRecord<Long, Person> record,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
                       Acknowledgment acknowledgment) {
        try {
            String topic = record.topic();
            Object key1 = record.key();
            String key = key1 == null ? "null" : key1.toString();
            Person value = record.value();
            String att = "";
            Iterator<org.apache.kafka.common.header.Header> attachment = record.headers().headers("attachment").iterator();
            if (attachment.hasNext()) {
                att = new String(attachment.next().value(), Charsets.UTF_8);
            }
            long offset = record.offset();
            int partition = record.partition();

            System.out.println("-->Person消费者, " + Thread.currentThread().getName() + " [id = " + value.getId() + ", topic = " + topic + ", partition = " + partition + ", offset = " + offset + ", offsets = " + offsets + ", att = " + att + "], key = " + key + ", value = " + value);

            if(flagClass.flag && record.partition() % 2 == 0){
                return;
            }




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

        acknowledgment.acknowledge();
    }

    public void seek(String topic, int partition, long offset){
        this.seekCallBack.get().seek(topic, partition, offset);
        System.out.println("成功 seek");
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback consumerSeekCallback) {
        this.seekCallBack.set(consumerSeekCallback);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {

    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {

    }
}
