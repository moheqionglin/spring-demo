package com.moheqionglin.nettyCustomrProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wanli.zhou
 * @description
 * @time 01/11/2018 11:34 AM
 */

public class Client {

    public static void main(String[] args)  {
        NioEventLoopGroup readWriteEventLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(readWriteEventLoop)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                writeFile(ctx);
                            }
                            private void writeFile(ChannelHandlerContext ctx) {
                                System.out.println("Connect");
                                try {
                                    Files.newDirectoryStream(Paths.get(getFilePath())).forEach(path -> {
                                        String fileName = path.toFile().getName();
                                        try {
                                            FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
                                            ByteBuffer socketBuffer = ByteBuffer.allocate(100);


                                            int fileSize = (int) fileChannel.size();
                                            byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
                                            int fileNameLength = fileNameBytes.length;

                                            //文件名长度+文件长度
                                            socketBuffer.putInt(fileSize + fileNameLength );
                                            socketBuffer.putInt(fileSize);

                                            System.out.println(">>>>开始发送 " + fileName + " 大小 " + fileSize);
                                            //发送文件名
                                            for(int index = 0; index < fileNameLength; index++){
                                                if(socketBuffer.remaining() > 0){
                                                    socketBuffer.put(fileNameBytes[index]);
                                                }else{
                                                    socketBuffer.flip();
                                                    ctx.write(Unpooled.wrappedBuffer(socketBuffer));
                                                    ctx.flush();
                                                    socketBuffer.clear();
                                                    socketBuffer.put(fileNameBytes[index]);
                                                }
                                            }
                                            //发送文件正文
                                            for( ;fileChannel.read(socketBuffer) > 0; ){
                                                socketBuffer.flip();
                                                if(socketBuffer.hasRemaining()){
                                                    ctx.write(Unpooled.wrappedBuffer(socketBuffer));
                                                    ctx.flush();
                                                }
                                                socketBuffer.clear();
                                            }
                                            System.out.println("||>>结束发送 " + fileName + " 大小 " + fileSize + "-> " + socketBuffer.remaining());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                            private String getFilePath(){
                                return this.getClass().getClassLoader().getResource("./images").getPath();
                            }

                        });
                    }
                });

        try {
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 9898).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readWriteEventLoop.shutdownGracefully();
        }

    }
}