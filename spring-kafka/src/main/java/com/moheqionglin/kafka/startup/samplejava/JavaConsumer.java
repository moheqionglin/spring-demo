package com.moheqionglin.kafka.startup.samplejava;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @ClassName : JavaConsumer
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-20 16:21
 *
 * kafkaconsumer 不是线程安全的，
 * 创建一个线程模型    Thread  ---  KafkaConsumer --- partition
 *   1个线程 对应 1个 kafkaConsumer 对应 1个 partition
 */
public class JavaConsumer {

    private KafkaConsumerFactory kafkaConsumerFactory = new KafkaConsumerFactory();
    private Runnable consumerThread = () -> {
        KafkaConsumer<Long, String> consumer = kafkaConsumerFactory.createConsumer(null);
        while (true){
            ConsumerRecords<Long, String> records = consumer.poll(1000);

            for (ConsumerRecord<Long, String> record : records) {
                System.out.println("[ThreadId="+Thread.currentThread().getId()+"], <partition " + record.partition() +
                        ">, <offset = " + record.offset() +
                        ">, <k ="+record.key()+">, <v=" + record.value()+">");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    };

    public void start(int totalPartitionNum){
        for(int i = 0 ; i < totalPartitionNum; i ++){
            new Thread(consumerThread).start();
        }
    }

    public static void main(String[] args) {
        new JavaConsumer().start(9);
    }
}