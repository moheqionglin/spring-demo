package com.moheqionglin.reactor;

import org.apache.commons.lang.CharSet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-30 20:53
 */
public class OpWriteNio {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    Selector selector = Selector.open();
    final Charset UTF8 = Charset.forName("utf-8");


    public OpWriteNio() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        OpWriteNio opWriteNio = new OpWriteNio();
        opWriteNio.init();
        opWriteNio.dispatch();

    }

    public void init() throws IOException {

        serverSocketChannel.bind(new InetSocketAddress(9909))
                .configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public void dispatch(){
        for(;;){
            try {
                selector.select();

                for(Iterator<SelectionKey> iterator = selector.selectedKeys().iterator(); iterator.hasNext();){
                    SelectionKey sk = iterator.next();
                    if(sk.isAcceptable()){
                        handlerAcceptAction(sk);
                    }else if(sk.isWritable()){
                        handlerWriteAction(sk);
                    }else if (sk.isReadable()){
                        handlerReadAction(sk);
                    }
                    iterator.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handlerReadAction(SelectionKey sk) throws IOException {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("\n" + UTF8.decode(byteBuffer));


        //开始大量写入， 注册些写事件

    }

    private void handlerWriteAction(SelectionKey sk) {

        System.out.print(".");
    }

    private void handlerAcceptAction(SelectionKey sk) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(sk.selector(), SelectionKey.OP_READ);
        socketChannel.write(ByteBuffer.wrap("hello nio channel!!!".getBytes()));
    }
}