package com.moheqionglin.explicitjavacode.blockClient;

import com.moheqionglin.explicitjavacode.message.Order;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.math.BigDecimal;

public class BlockProducer {

    public static void main(String[] args) throws JMSException {

        BlockProducer producer = new BlockProducer();
        Session session = producer.createSession();
        MessageProducer messageProducer = producer.getProducer(session);


        try {
            for(int i = 0  ; i < 1000 ; i++){
                TextMessage txtMsg = session.createTextMessage();
                txtMsg.setText( "发送textmessage消息" + i);
                txtMsg.setStringProperty("txt-key", "txt-value-" + i);
                txtMsg.setIntProperty("index-key", i);
                messageProducer.send(txtMsg);

                ObjectMessage objMsg = session.createObjectMessage();
                objMsg.setObject(new Order(i, i, BigDecimal.valueOf(1 * 0.8),"东靖路" + i + "号"));
                objMsg.setStringProperty("object-key", "object-value-" + i);
                objMsg.setIntProperty("index-key", i);
                messageProducer.send(objMsg);
            }


        } finally {
            messageProducer.close();
        }

        System.out.println("send message success!");

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
