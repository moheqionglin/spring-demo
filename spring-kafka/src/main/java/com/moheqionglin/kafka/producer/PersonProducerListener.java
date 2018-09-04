package com.moheqionglin.kafka.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
public class PersonProducerListener implements ProducerListener {
    @Override
    public void onSuccess(String s, Integer integer, Object o, Object o2, RecordMetadata recordMetadata) {
        System.out.println("-->>生产者 成功" + s + ", " + integer + ", " + o + ", " + o2 + ", " + recordMetadata);
    }

    @Override
    public void onError(String s, Integer integer, Object o, Object o2, Exception e) {
        System.out.println("-->>生产者 失败" + s + ", " + integer + ", " + o + ", " + o2 + ", " + e.getMessage());
    }

    @Override
    public boolean isInterestedInSuccess() {
        return true;
    }
}
