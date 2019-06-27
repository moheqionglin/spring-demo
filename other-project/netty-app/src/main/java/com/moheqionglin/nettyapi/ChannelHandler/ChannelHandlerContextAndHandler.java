package com.moheqionglin.nettyapi.ChannelHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-17 09:54
 */
public class ChannelHandlerContextAndHandler {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //线程模型是 一个Thread-> EventLoop -> 多个 channel
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

    }
}