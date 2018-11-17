package com.moheqionglin.nioVsTrandionalIo.blockOrNonBlock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author wanli.zhou
 * @description
 * @time 25/10/2018 9:35 AM
 */
public class NioWithoutSelector {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress(8900));
        serverSocketChannel.configureBlocking(true);
        for(;;){
            SocketChannel socketChannel = serverSocketChannel.accept();
            new Thread(()->{
                System.out.println("-> New socket connect.");
                ByteBuffer byteBuffer = ByteBuffer.allocate(2);
                try{
                    for(; socketChannel.read(byteBuffer) > 0; ){
                        byteBuffer.flip();
                        System.out.println(StandardCharsets.UTF_8.decode(byteBuffer));
                        byteBuffer.clear();
                    }
                }catch (Exception e){

                }

            }).start();

        }
    }
}