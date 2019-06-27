package com.moheqionglin.nettyapi.ChannelContextVSChannel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-03 10:37
 */
public class ChannelHandler1 extends SimpleChannelInboundHandler<String> {
    public ChannelHandler1(){
        super(false);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        msg = "[ChannelHandler1 => " + msg + "]";
        System.out.println(msg);
//        ctx.writeAndFlush("ChannelHandler1.context.write " + msg);
//        ctx.channel().writeAndFlush("ChannelHandler1.channel.write " + msg);
        ctx.fireChannelRead(msg);
    }
}