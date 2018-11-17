package com.moheqionglin.backlog.demo1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

/**
 * @author wanli.zhou
 * @description
 * @time 29/10/2018 2:19 PM
 */
public class Server {

    public static void main(String[] args) throws IOException {

        java.nio.channels.ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //设置backlog是3
        serverSocketChannel.bind(new InetSocketAddress(9981), 3)
                .configureBlocking(true);

        //这里我们不从 内核态中的backlog队列中 消费待处理的链接。
        for(;;){
            //不做任何处理
        }

    }
}