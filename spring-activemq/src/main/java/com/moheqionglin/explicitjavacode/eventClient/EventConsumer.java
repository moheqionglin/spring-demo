package com.moheqionglin.explicitjavacode.eventClient;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventConsumer {

    public static void main(String[] args) throws JMSException {

        EventConsumer consumer = new EventConsumer();
        MessageConsumer messageConsumer = consumer.getConsumer(consumer.createSession());

        messageConsumer.setMessageListener(new EventConsumerMessgeListener());
        System.out.println("receive message success");

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
