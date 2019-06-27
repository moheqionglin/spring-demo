package com.moheqionglin.nettyapi.ChannelContextVSChannel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-03 10:33
 */
public class ChannelContextVsChannelServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup mainGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ChannelFuture channelFuture = serverBootstrap.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                        .addLast(new StringDecoder())
                        .addLast(new StringEncoder())
                        .addLast(new ChannelHandler1())
                        .addLast(new ChannelHandler2())
                        .addLast(new ChannelHandler3())
                        .addLast(new ChannelHandler4())
//                        .addLast(new ChannelOutboundHandler1())
//                        .addLast(new ChannelOutboundHandler2())
                        ;

                    }
                }).bind(9901).sync();

        channelFuture.channel().closeFuture().sync();

    }
}