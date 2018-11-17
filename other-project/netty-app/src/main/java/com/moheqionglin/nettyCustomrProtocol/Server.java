package com.moheqionglin.nettyCustomrProtocol;

import com.moheqionglin.nettyCustomrProtocol.codec.codec1.CustomProtocolChannelInitializer1;
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
 * @time 27/10/2018 11:04 PM
 */
public class Server {

    //GET 1.png
    //GET ALL FILES
    //POST
    public static void main(String[] args) {
        new Server().startServer(9898);
    }

    public void startServer(Integer port){
        EventLoopGroup connectorEventGroup = new NioEventLoopGroup();
        EventLoopGroup readWriteEventGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(connectorEventGroup, readWriteEventGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new CustomProtocolChannelInitializer1());
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("Start Server at " + port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            connectorEventGroup.shutdownGracefully();
            readWriteEventGroup.shutdownGracefully();
        }
    }
}