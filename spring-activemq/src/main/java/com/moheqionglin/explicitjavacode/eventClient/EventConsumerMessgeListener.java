package com.moheqionglin.explicitjavacode.eventClient;

import com.moheqionglin.MessageHandler;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class EventConsumerMessgeListener implements MessageListener {
    @Override
    public void onMessage(javax.jms.Message message) {
        try {
            MessageHandler.processMessage(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
