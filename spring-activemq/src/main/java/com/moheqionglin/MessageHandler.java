package com.moheqionglin;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class MessageHandler {

    public static void processMessage(Message message) throws JMSException {
        if(message instanceof TextMessage){
            TextMessage txtMsg = (TextMessage)message;
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("######          Message Details           #####");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("message : " + txtMsg.getText());
            System.out.println("property : txt-key : " + txtMsg.getStringProperty("txt-key") );
            System.out.println("property : index-key: " + txtMsg.getIntProperty("index-key") );
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
        }else if(message instanceof ObjectMessage){
            ObjectMessage objMsg = (ObjectMessage) message;
            Object obj = objMsg.getObject();
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("######          Message Details           #####");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("message : " + obj);
            System.out.println("property : object-key : " + objMsg.getStringProperty("object-key") );
            System.out.println("property : index-key: " + objMsg.getIntProperty("index-key") );
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
        }

    }
}
