package com.moheqionglin.nettyFull;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wanli.zhou
 * @description
 * @time 01/11/2018 2:53 PM
 */
public class Server {

    public static void main(String[] args) {
        NioEventLoopGroup connectEventLoopGroup = new NioEventLoopGroup();
        NioEventLoopGroup readWriteLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(connectEventLoopGroup, readWriteLoopGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler());


    }
}