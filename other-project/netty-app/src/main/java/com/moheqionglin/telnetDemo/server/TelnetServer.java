package com.moheqionglin.telnetDemo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wanli.zhou
 * @description
 * @time 22/10/2018 10:04 AM
 */
public class TelnetServer {

    public static void main(String[] args) {
        new TelnetServer().start();
    }

    public void start(){
        //接受client链接的 event loop group
        EventLoopGroup connectorEventLoopGroup = new NioEventLoopGroup(1);
        //处理SocketChannel的线程组,接受数据的读写操作。
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(1);
        try {

            ServerBootstrap server = new ServerBootstrap();
            ChannelFuture future = server.group(connectorEventLoopGroup, workerEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer())
                    .bind(8989);

            System.out.println();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            connectorEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }

    }


}