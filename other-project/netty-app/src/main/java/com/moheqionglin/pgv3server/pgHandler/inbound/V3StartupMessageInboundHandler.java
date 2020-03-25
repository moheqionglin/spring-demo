package com.moheqionglin.pgv3server.pgHandler.inbound;

import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.decoderForFront.V3StartupMessage;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3AuthenticationMD5Password;
import com.moheqionglin.pgv3server.tools.PgSessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V3StartupMessageInboundHandler extends SimpleChannelInboundHandler<V3StartupMessage> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3StartupMessage simpleQuery) throws Exception {
        String md5Salt = PgSessionManager.getSessionVal(ctx.channel().id(), PgSessionManager.MD5_SALT, "-1");
        ctx.writeAndFlush(new V3AuthenticationMD5Password(Integer.valueOf(md5Salt)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}