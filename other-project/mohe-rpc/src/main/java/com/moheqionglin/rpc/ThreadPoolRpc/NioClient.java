package com.moheqionglin.rpc.ThreadPoolRpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-01 15:38
 */
public class NioClient {
    static final Charset charset = Charset.forName("utf-8");

    public static void main(String[] args) throws IOException {

        for(int i = 0 ; i < 10; i ++){
            new Thread(){
                @Override
                public void run() {
                    Selector selector = null;
                    try {
                        selector = Selector.open();

                        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9988));
                        socketChannel
                                .configureBlocking(false)
                                .register(selector, SelectionKey.OP_READ);
                        socketChannel.write(ByteBuffer.wrap((System.currentTimeMillis() + "").getBytes(charset)));
                        long start = System.currentTimeMillis();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
                        while (true){
                            int select = selector.select(3000);
                            if(select > 0){
                                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                                for(Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext();){
                                    SelectionKey key = iterator.next();
                                    if(key.isReadable()){
                                        SocketChannel sc = (SocketChannel) key.channel();
                                        sc.read(byteBuffer);
                                        byteBuffer.flip();
                                        System.out.println((System.currentTimeMillis() - start) / 1000 + "=>" + charset.decode(byteBuffer));
                                        byteBuffer.clear();
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }

}