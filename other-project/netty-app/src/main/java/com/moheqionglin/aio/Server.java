package com.moheqionglin.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wanli.zhou
 * @description
 * @time 29/10/2018 6:00 PM
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        asynchronousServerSocketChannel.bind(new InetSocketAddress(9985));



        asynchronousServerSocketChannel.accept("accept attachment handler", new CompletionHandler<AsynchronousSocketChannel, String>(){

            @Override
            public void completed(AsynchronousSocketChannel channel, String attachment) {
                System.out.println("-->开始读取");
                ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
                channel.read(byteBuffer, byteBuffer1, new CompletionHandler<Integer, ByteBuffer>(){

                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        attachment.flip();
                        byteBuffer.flip();
                        String readStr = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                        System.out.println("-->" + result + ", " +  StandardCharsets.UTF_8.decode(attachment) + ", " + readStr);
                        attachment.clear();
                        byteBuffer.clear();

                        channel.write(ByteBuffer.wrap(("接收到数据: " + readStr + "\n").getBytes(StandardCharsets.UTF_8)));

                        //循环读取
                        channel.read(byteBuffer, byteBuffer1, this);
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } );

                System.out.println("-->|||");
            }

            @Override
            public void failed(Throwable exc, String attachment) {
            }
        });

        synchronized (Server.class){
            Server.class.wait();
        }


    }
}