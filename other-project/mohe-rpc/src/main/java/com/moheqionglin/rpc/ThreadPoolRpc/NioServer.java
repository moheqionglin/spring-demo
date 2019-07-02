package com.moheqionglin.rpc.ThreadPoolRpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-01 15:18
 */
public class NioServer {

    ExecutorService threadPool = Executors.newFixedThreadPool(4);

    Charset charset = Charset.forName("UTF-8");


    public static void main(String[] args) throws IOException {

        NioServer nioServer = new NioServer();
        nioServer.export();
    }

    public void export() throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(9988))
                .configureBlocking(false)
                .register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("start server 9988");
        int i = 0;

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);

        while (true){

            int select = selector.select(3000);
            System.out.println("select " + i++);
            if(select <= 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for(Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext();){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel soChannel = serverSocketChannel.accept();
                    soChannel.configureBlocking(false);
                    soChannel.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    threadPool.submit(new SelfTask(charset.decode(byteBuffer).toString(), socketChannel));
                    byteBuffer.clear();
                }
            }
        }

    }

}