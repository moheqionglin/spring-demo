package com.moheqionglin.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import java.util.Arrays;

public class Listener1 implements MessageListener<Object, String> {

    @Override
    public void onMessage(ConsumerRecord<Object, String> record) {
        String topic = record.topic();
        String key = record.key().toString();
        String value = record.value();
        long offset = record.offset();
        int partition = record.partition();
        System.out.println(Arrays.asList(new Object[]{topic, key, value, offset, partition}));
    }
}