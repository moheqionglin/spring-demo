package com.moheqionglin.rpc.ThreadPoolRpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-01 17:39
 */
public class BioServer {

    Charset charset = Charset.forName("utf-8");

    ExecutorService threadPool = new ThreadPoolExecutor(1, 1,
            1L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("拒绝");
        }
    });


    public static void main(String[] args) throws IOException {
        new BioServer().export();
    }

    public void export() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9987))
                .configureBlocking(true);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            threadPool.submit(new SelfTask(charset.decode(byteBuffer).toString(), socketChannel));
            System.out.println("receive one task");
            byteBuffer.clear();
        }

    }

}