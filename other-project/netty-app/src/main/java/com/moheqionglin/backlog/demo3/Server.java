package com.moheqionglin.backlog.demo3;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author wanli.zhou
 * @description
 * @time 29/10/2018 2:19 PM
 */
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置backlog是3
        serverSocketChannel.bind(new InetSocketAddress(9983), 2)
                .configureBlocking(true);

        //这里我们不从 内核态中的backlog队列中 消费待处理的链接。
        for(int i = 1; ; i++){
            //不做任何处理
            SocketChannel accept = serverSocketChannel.accept();
            System.out.println("处理 socket " + i);
            Thread.sleep(2* 10000);
        }

    }
}