package com.moheqionglin.pgv3server.pgHandler.inbound;

import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.decoderForFront.V3Terminate;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V3TerminateInboundHandler extends SimpleChannelInboundHandler<V3Terminate> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3Terminate msg) throws Exception {
        log.info("Receive V3Terminate message, close from {}", V3Utils.getRemoteIp(ctx));
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}
