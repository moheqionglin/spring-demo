package com.moheqionglin.backlog.demo3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author wanli.zhou
 * @description
 * @time 29/10/2018 2:23 PM
 */
public class Client {

    /**
     * Client 连接 Server 0
     * Client 连接 Server 1
     * Client 连接 Server 2
     * Exception in thread "main" java.net.ConnectException: Operation timed out
     * 	at sun.nio.ch.Net.connect0(Native Method)
     * 	at sun.nio.ch.Net.connect(Net.java:454)
     * 	at sun.nio.ch.Net.connect(Net.java:446)
     * 	at sun.nio.ch.SocketChannelImpl.connect(SocketChannelImpl.java:648)
     * 	at java.nio.channels.SocketChannel.open(SocketChannel.java:189)
     * 	at com.moheqionglin.backlog.demo1.Client.main(Client.java:16)
     *
     * */
    public static void main(String[] args) throws IOException {

        for(int i = 1; i <= 10 ; i ++){
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9983));
            socketChannel.configureBlocking(true);
            System.out.println("Client 连接 Server " + i);
        }
    }

}