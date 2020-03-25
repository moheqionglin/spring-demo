package com.moheqionglin.pgv3server.pgHandler.inbound;

import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.decoderForFront.V3Parse;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ParseComplete;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class V3ParserInboundHandler extends SimpleChannelInboundHandler<V3Parse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3Parse parse) throws Exception {
        ctx.writeAndFlush(new V3ParseComplete());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}