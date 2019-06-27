package com.moheqionglin.nettyapi.ChannelContextVSChannel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-03 11:00
 */
public class ChannelOutboundHandler1 extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        msg = " ChannelOutboundHandler1 " + msg;
        System.out.println(msg);
        ctx.writeAndFlush(msg, promise);
    }
}