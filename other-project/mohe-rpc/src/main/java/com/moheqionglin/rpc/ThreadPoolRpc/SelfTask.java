package com.moheqionglin.rpc.ThreadPoolRpc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SelfTask implements Runnable{
        private String inboundMessage;
        private SocketChannel channel;
        Charset charset = Charset.forName("UTF-8");

        public SelfTask(String message, SocketChannel channel){
            this.inboundMessage = message;
            this.channel = channel;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId() + " receive message" + inboundMessage);
            inboundMessage = " before process " + inboundMessage;
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inboundMessage += ", after process";
            System.out.println(Thread.currentThread().getId() + " after process message" + inboundMessage);
            try {
                channel.write(ByteBuffer.wrap(inboundMessage.getBytes(charset)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " after flush message" + inboundMessage);
        }
    }