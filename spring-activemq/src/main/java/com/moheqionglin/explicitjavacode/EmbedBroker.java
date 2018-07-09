package com.moheqionglin.explicitjavacode;

import org.apache.activemq.broker.BrokerService;

import java.net.URL;

public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61616");
        broker.setDataDirectory(kahadbDir());
        broker.start();
        System.out.println("start Broker success!");
        Object lock = new Object();
        synchronized (lock){
            lock.wait();
        }
    }

    private static String kahadbDir() {
        URL resource = EmbedBroker.class.getResource("");
        String path = resource.getFile();

        String kahaDbPath = path.substring(0, path.indexOf("target/"));
        System.out.println(kahaDbPath);
        return kahaDbPath;
    }
}
