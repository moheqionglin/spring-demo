package com.moheqionglin.telnetDemo.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author wanli.zhou
 * @description
 * @time 15/11/2018 11:25 AM
 */
public class AfterProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            ctx.fireChannelRead(msg + "==> After Process ");
        }

    }
}