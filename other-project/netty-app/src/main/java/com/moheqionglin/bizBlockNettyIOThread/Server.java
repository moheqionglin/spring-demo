package com.moheqionglin.bizBlockNettyIOThread;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-27 16:58
 */
public class Server {
    public static void main(String[] args) {


        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ChannelFuture channelFuture = serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new CustomHandler())
                .bind(9808);
        System.out.println(1);
        try {
            channelFuture.sync();
            System.out.println(2);
            channelFuture.channel().closeFuture().addListener(new GenericFutureListener(){

                @Override
                public void operationComplete(Future future) throws Exception {
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                    System.out.println("Gracefully shut down");
                }
            }).sync();
            System.out.println(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("14");


    }
}