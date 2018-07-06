package com.moheqionglin.BrokerFactory;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Comsumer {
    public static void main(String[] args) throws JMSException {

        Comsumer consumer = new Comsumer();
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
            TextMessage message = (TextMessage) consumer.receive(1000);
            if(message == null){
                Thread.sleep(100l);
                continue;
            }
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("######          Message Details           #####");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("message: " + message.getText());
            System.out.println("session: " + consumer.hashCode());
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
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
