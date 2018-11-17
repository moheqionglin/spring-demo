package com.moheqionglin.telnetDemo.server;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import sun.jvm.hotspot.debugger.win32.coff.DebugVC50TypeLeafIndices;

/**
 * @author wanli.zhou
 * @description
 * @time 22/10/2018 10:10 AM
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //A decoder that splits the received ByteBufs by one or more delimiters. It is particularly useful for decoding the frames which ends with a delimiter such as NUL or newline characters.
        //选择10kb的 data frame length， 分隔符是 换行符\r \n
        pipeline.addLast(new LoggingHandler(LogLevel.INFO))
                .addLast(new DelimiterBasedFrameDecoder(1024 * 8, Delimiters.lineDelimiter()))
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new PreProcessHandler())
                .addLast(new AfterProcessHandler())
                .addLast(new ServerHandler());



    }
}