package com.moheqionglin.pgv3server.pgHandler;

import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.exception.MessageIncompleteException;
import com.moheqionglin.pgv3server.tools.PgSessionManager;
import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import com.moheqionglin.pgv3server.protocol.V3ProtocolDecoderManager;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class V3ProtocolByteToMessageDecoder extends ByteToMessageDecoder {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ChannelId id = ctx.channel().id();
        String hostAddress = V3Utils.getRemoteIp(ctx);
        PgSessionManager.initSession(id);
        log.info("Connect success from client ip [{}], init session success {} -> {}", hostAddress, id, PgSessionManager.getSessionVal(id, PgSessionManager.MD5_SALT, "null"));
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        ChannelId id = ctx.channel().id();
        String hostAddress = V3Utils.getRemoteIp(ctx);
        HashMap<String, String> allSessionVal = PgSessionManager.getAllSessionVal(id);
        PgSessionManager.clearSession(id);

        log.info("Break connect from client ip [{}], clear session success for {} -> []", hostAddress, id, allSessionVal);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if(!makeSureReadableBytes(byteBuf, 1)){
            return;
        }
        List<BaseProtocolDecoder> decoders = V3ProtocolDecoderManager.getInstance().getDecoders();
        boolean decodeSuccess = false;

        for(Iterator<BaseProtocolDecoder> iterator = decoders.iterator(); iterator.hasNext();){
            boolean accept = false;
            BaseProtocolDecoder decoder = iterator.next();
            try {
                accept = decoder.accept(byteBuf);
            } catch (MessageIncompleteException e) {
                log.warn("message in complete");
                return;
            }

            if(accept){
                BaseProtocolDecoder decoder1 = null;
                try {
                    decoder1 = decoder.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("", e);
                }

                decoder1.decode(byteBuf);
                list.add(decoder1);

                decodeSuccess = true;
                break;
            }
        }
        if(!decodeSuccess){
            log.warn("error process");
        }
    }

    private boolean makeSureReadableBytes(ByteBuf byteBuf, int size) {
        return byteBuf.readableBytes() >= size;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }

}
