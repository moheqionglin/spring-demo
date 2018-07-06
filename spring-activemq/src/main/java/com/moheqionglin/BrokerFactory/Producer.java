package com.moheqionglin.BrokerFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

    public static void main(String[] args) throws JMSException {

        Producer producer = new Producer();
        Session session = producer.createSession();
        MessageProducer messageProducer = producer.getProducer(session);

        try {
            for(int i = 0  ; i < 1000 ; i++)
            producer.sendMessage(session, messageProducer, "发送消息" + i);
        } finally {
            messageProducer.close();
        }

        System.out.println("send message success!");

    }

    private void sendMessage(Session session, MessageProducer messageProducer, String messageStr) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setText(messageStr);
        messageProducer.send(message);
    }


    private MessageProducer getProducer(Session session) throws JMSException {

        Destination destination = session.createQueue("MyQueue");
        MessageProducer producer = session.createProducer(destination);
        return producer;
    }

    private Session createSession() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }
}
