package com.moheqionglin.telnetDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Iterator;

/**
 * @author wanli.zhou
 * @description
 * @time 24/10/2018 2:07 PM
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9988));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            int select = selector.select();
            System.out.println("--> selector.select()");
            if(select > 0){
                for(Iterator<SelectionKey> iterator = selector.selectedKeys().iterator(); iterator.hasNext(); ){
                    SelectionKey next = iterator.next();
                    if(next.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                        System.out.println("链接成功");
                        iterator.remove();
                    }else if(next.isReadable()){
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        for(;channel.read(byteBuffer) > 0;){
                            byteBuffer.flip();
                            System.out.println(StandardCharsets.UTF_8.decode(byteBuffer));
                            byteBuffer.clear();
                        }
                        iterator.remove();
                    }
                }
            }
        }
    }
}