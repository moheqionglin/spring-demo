package com.moheqionglin.nioVsTrandionalIo.zeroCopy;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author wanli.zhou
 * @description
 * @time 23/11/2018 12:13 AM
 */
public class Client {
    public static void main(String[] args) throws IOException {
        for(int i = 0 ; i < 10 ; i ++)
        nioReceive();
    }

    private static void nioReceive() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8798));
        socketChannel.configureBlocking(true);
        ByteBuffer byteBuffer = ByteBuffer.allocate(10000);
        for(int length = 0; (length = socketChannel.read(byteBuffer)) > 0; );
    }

}