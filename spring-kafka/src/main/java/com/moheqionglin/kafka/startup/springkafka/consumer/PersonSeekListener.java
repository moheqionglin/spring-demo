//package com.moheqionglin.kafka.startup.springkafka.consumer;
//
//import com.moheqionglin.kafka.KafkaConfig;
//import com.moheqionglin.kafka.common.person.Person;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.TopicPartition;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.listener.ConsumerSeekAware;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//
//public class PersonSeekListener implements ConsumerSeekAware {
//
//    private final ThreadLocal<ConsumerSeekCallback> seekCallBack = new ThreadLocal<>();
//    private final CountDownLatch latch1 = new CountDownLatch(1);
//
//
//    @KafkaListener(id = "person-seek-consumer-g",
//            topics = KafkaConfig.personTopic,
//            containerFactory = "personKafkaListenerContainerFactory")
//    public void listenBytes(ConsumerRecord<Long, Person> record,
//                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
//                       Acknowledgment acknowledgment) {
//        String topic = record.topic();
//        Object key1 = record.key();
//        String key = key1 == null ? "null" : key1.toString();
//        Person value = record.value();
//
//        long offset = record.offset();
//        int partition = record.partition();
//
//        try {
//            System.out.println("-->Person消费者, " + Thread.currentThread().getName() + " [id = " + value.getId() + ", topic = " + topic + ", partition = " + partition + ", offset = " + offset + ", offsets = " + offsets + "], key = " + key + ", value = " + value);
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(value.getId() == 17 || value.getId() == 16 || value.getId() == 15 ||
//                    value.getId() == 14 || value.getId() == 13 || value.getId() == 12||
//                    value.getId() == 11 || value.getId() == 19 || value.getId() == 18){
//                int i = 1 / 0;
//            }
//            System.out.println("====>||||||====<");
//            acknowledgment.acknowledge();
//
//        }catch (Exception e){
//            System.out.println("ERROR " + value.getId() + " , " + record.partition() + ", "+ e.getMessage());
//            //0- 300 303 306 309
//            //1- 301 304 307
//            //2- 302 305 308
////            if(value.getId() == 306 || value.getId() == 304 || value.getId() == 305){
////                acknowledgment.acknowledge();
////            }
//            this.seekCallBack.get().seek(topic, partition, offset);
//        }
//
//    }
//
//    public void seek(String topic, int partition, long offset){
//        this.seekCallBack.get().seek(topic, partition, offset);
//        System.out.println("成功 seek");
//    }
//
//    @Override
//    public void registerSeekCallback(ConsumerSeekCallback consumerSeekCallback) {
//        this.seekCallBack.set(consumerSeekCallback);
//    }
//
//    @Override
//    public void onPartitionsAssigned(final Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {
//        JFrame frame = new JFrame("重新加入新的consumer,reblance");
//        frame.setSize(new Dimension(300, 400));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new JPanel(){
//            @Override
//            public void paint(Graphics g){
//                g.drawString(map.toString(), 20 , 20);
//            }
//        });
//        frame.setVisible(true);
//        System.out.println("====>>>>>>>>>>>>>>>>>>>>>>>");
//    }
//
//    @Override
//    public void onIdleContainer(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {
//
//    }
//}
