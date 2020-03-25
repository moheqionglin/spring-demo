package com.moheqionglin.pgv3server.pgHandler.inbound;

import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.decoderForFront.V3SSLRequest;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3NoticeResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class V3SSLRequestInboundHandler extends SimpleChannelInboundHandler<V3SSLRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3SSLRequest msg) throws Exception {
        ctx.writeAndFlush(new V3NoticeResponse(4, (byte)0, ""));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}