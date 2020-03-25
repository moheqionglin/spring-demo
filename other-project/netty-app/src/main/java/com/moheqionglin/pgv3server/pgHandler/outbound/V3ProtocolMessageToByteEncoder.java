package com.moheqionglin.pgv3server.pgHandler.outbound;

import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class V3ProtocolMessageToByteEncoder extends MessageToByteEncoder<BaseProtocolEncoder> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseProtocolEncoder message, ByteBuf byteBuf)  {
        message.encode(byteBuf);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}
