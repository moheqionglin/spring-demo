package com.moheqionglin.BrokerFactory;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {
    private final String connectionUri = "tcp://localhost:61616";

    public static void main(String[] args) throws Exception {
        BrokerService service = BrokerFactory.createBroker("xbean:activemq.xml");
        service.start();

        System.out.println("start Broker success!");
    }
}
