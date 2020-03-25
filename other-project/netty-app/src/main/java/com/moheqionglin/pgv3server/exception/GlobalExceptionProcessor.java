package com.moheqionglin.pgv3server.exception;

import com.moheqionglin.pgv3server.tools.PgSessionManager;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ErrorResponse;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.OutOfDirectMemoryError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GlobalExceptionProcessor {
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionProcessor.class);

    public static void processException(ChannelHandlerContext ctx, Throwable cause) {
        String remoteIp = V3Utils.getRemoteIp(ctx);
        log.error("Process exception, from remote ip :" + remoteIp + ", current session: " + PgSessionManager.getAllSessionVal(ctx.channel().id()), cause);
        if (cause instanceof OutOfDirectMemoryError) {
            ctx.channel().close();
            log.error("close connection from ip: " + remoteIp, cause);
            return;
        }
        ctx.writeAndFlush(new V3ErrorResponse(cause));
        return ;
    }


}