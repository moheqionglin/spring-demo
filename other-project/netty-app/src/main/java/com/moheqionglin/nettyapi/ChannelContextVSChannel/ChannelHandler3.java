package com.moheqionglin.nettyapi.ChannelContextVSChannel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-03 10:37
 */
public class ChannelHandler3 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        msg = "[ ChannelHandler3 => " + msg + "]";
        System.out.println(msg);
//        ctx.writeAndFlush("ChannelHandler3.context.write " + msg);
//        ctx.channel().writeAndFlush("ChannelHandler3.channel.write " + msg);
        ctx.fireChannelRead(msg);
    }
}