package com.moheqionglin.nettyCustomrProtocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.SocketAddress;

/**
 * @author wanli.zhou
 * @description
 * @time 31/10/2018 5:46 PM
 */
public class CustomProtocolChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final int ONE_M = 1024 * 1024 * 1024;

    /**
     *
     * @param channel
     * @throws Exception
     *
     *
     *  fileNameLength + fileLength | FileNameLength | FileName | FileContent
     *
     */
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(ONE_M, 0,4 ,4, 0 ))
                .addLast(new FileSaveHandler())
                ;


    }
}