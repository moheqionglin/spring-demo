package customRpc.springdubbo;

import info.batey.kafka.unit.KafkaUnit;

/**
 * @author wanli.zhou
 * @description
 * @time 07/01/2019 3:28 PM
 */

public class KafkaStarter {
    public static void main(String[] args) {
        KafkaUnit kafkaUnitServer = new KafkaUnit(2188, 9093);
        kafkaUnitServer.startup();
    }
}