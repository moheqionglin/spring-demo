package com.moheqionglin.kafka.kafkaunit;

import info.batey.kafka.unit.KafkaUnit;

import java.io.IOException;

/**
 * @author wanli.zhou
 * @description
 * @time 24/12/2018 5:20 PM
 */
public class Test {
    public static void main(String[] args) throws IOException {
        KafkaUnit kafkaUnitServer = new KafkaUnit(2187, 9091);
        kafkaUnitServer.startup();
//        kafkaUnitServer.shutdown();

    }

}