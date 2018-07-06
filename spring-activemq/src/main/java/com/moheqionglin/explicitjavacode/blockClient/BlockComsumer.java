package com.moheqionglin.explicitjavacode.blockClient;


import com.moheqionglin.MessageHandler;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class BlockComsumer {
    public static void main(String[] args) throws JMSException {

        BlockComsumer consumer = new BlockComsumer();
        MessageConsumer messageConsumer = consumer.getConsumer(consumer.createSession());
        try {
            consumer.receiveMessage(messageConsumer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            messageConsumer.close();
        }
        System.out.println("receive message success");

    }

    private void receiveMessage(MessageConsumer consumer) throws JMSException, InterruptedException {
        while (true){
            Message msg = consumer.receive(1000);
            if(msg == null){
                TimeUnit.SECONDS.sleep(1);
                continue;
            }
            MessageHandler.processMessage(msg);
        }

    }
    private MessageConsumer getConsumer(Session session) throws JMSException {
        Destination destination = session.createQueue("MyQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        return consumer;
    }

    private Session createSession() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }
}
