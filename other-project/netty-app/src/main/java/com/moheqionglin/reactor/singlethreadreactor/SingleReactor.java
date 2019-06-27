package com.moheqionglin.reactor.singlethreadreactor;

import javax.xml.stream.events.Characters;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-29 13:39
 */
public class SingleReactor {

    ByteBuffer readBuffer = ByteBuffer.allocate(100);
    ByteBuffer writeBuffer = ByteBuffer.allocate(100);

    public static void main(String[] args) {
        new SingleReactor().start();
    }

    public void start(){
        new Thread(new Reactor()).start();
    }

    class Reactor implements Runnable{
        Selector selector;
        ServerSocketChannel serverSocketChannel;
        public Reactor(){
            try {
                selector = Selector.open();
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(9903));
                serverSocketChannel.configureBlocking(false);
                SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                register.attach(new Acceptor(selector, serverSocketChannel));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (!Thread.interrupted()){
                try {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for(Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext();){
                        SelectionKey selKey = iterator.next();
                        dispatch(selKey);
                        iterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void dispatch(SelectionKey selKey) {
            Runnable attachment = (Runnable) selKey.attachment();
            if(attachment != null){
                attachment.run();
            }
        }
    }
    //处理ACCEPT事件
    class Acceptor implements Runnable{
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;

        public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
            this.selector = selector;
            this.serverSocketChannel = serverSocketChannel;
        }

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                SelectionKey sk = socketChannel.register(selector, SelectionKey.OP_READ);
                sk.attach(new ReadHandler(selector, socketChannel));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadHandler implements Runnable{
        private Selector selector;
        private SocketChannel socketChannel;

        final Charset UTF8 = Charset.forName("utf-8");

        public ReadHandler(Selector selector, SocketChannel socketChannel) {
            this.selector = selector;
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                readBuffer.clear();
                socketChannel.read(readBuffer);
                String rest = process(readBuffer);
                writeBuffer.put(rest.getBytes());

                //这个demo是模拟一次写入， 这里直接读完以后， 在完成一次写 就会关掉socket
                SelectionKey sk = socketChannel.register(selector, SelectionKey.OP_WRITE);
                sk.attach(new WriterHandler(selector, socketChannel));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String process(ByteBuffer byteBuffer) {
            byteBuffer.flip();
            String s =  "=>" + UTF8.decode(byteBuffer);
            System.out.println("读取 " + s);
            return s;
        }
    }

    class WriterHandler implements Runnable{
        private Selector selector;
        private SocketChannel socketChannel;

        final Charset UTF8 = Charset.forName("utf-8");
        public WriterHandler(Selector selector, SocketChannel socketChannel) {
            this.selector = selector;
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                writeBuffer.flip();
                System.out.println("写入 " + UTF8.decode(writeBuffer));
                socketChannel.write(writeBuffer);
                writeBuffer.clear();

                SelectionKey sk = socketChannel.register(selector, SelectionKey.OP_READ);
                sk.attach(new ReadHandler(selector, socketChannel));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}