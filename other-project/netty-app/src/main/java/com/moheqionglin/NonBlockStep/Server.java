package com.moheqionglin.NonBlockStep;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wanli.zhou
 * @description
 * @time 29/10/2018 2:55 PM
 */
public class Server {
    /**
     *
     * @param args
     * @throws IOException
     *
     * Non block 到底哪些步骤是非阻塞的？
     *   1.
     */
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9984));
        serverSocketChannel.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        SocketChannel accept = null;

        //************************************************************
        //********************NON  BLOCK******************************
        //************************************************************
        //注意accept是非阻塞的，因此要一直的获取连接
        while (accept == null){
            accept = serverSocketChannel.accept();
        }

        accept.configureBlocking(false);

        System.out.println("-->");
        int read = accept.read(byteBuffer);

        //************************************************************
        //********************NON  BLOCK******************************
        //************************************************************
        //读取也是非阻塞的， 返回值是0代表连接正常，但是操作系统的缓存区没有数据， -1 代表连接终止。
        while (read == 0){
            read = accept.read(byteBuffer);
        }


        byteBuffer.flip();
        if(read > 0){
            System.out.println( "Read length " + read + ", " + StandardCharsets.UTF_8.decode(byteBuffer).toString());
        }


    }
}