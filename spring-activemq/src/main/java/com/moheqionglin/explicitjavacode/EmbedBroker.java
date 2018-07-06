package com.moheqionglin.explicitjavacode;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        System.out.println("start Broker success!");
        Object lock = new Object();
        synchronized (lock){
            lock.wait();
        }
    }
}
