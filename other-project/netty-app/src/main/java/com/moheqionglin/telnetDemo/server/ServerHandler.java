package com.moheqionglin.telnetDemo.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author wanli.zhou
 * @description
 * @time 22/10/2018 10:33 AM
 */
//Handler 一般继承 ChannelInboundHandlerAdapter 或者 SimpleChannelInboundHandler类
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final String STARTER_STR = "[From Server Message]";

    /**
     *
     * @param context
     * 建立链接发送庆祝
     */
    @Override
    public void channelActive(ChannelHandlerContext context) throws UnknownHostException {
        context.write(STARTER_STR + " Welcome to " + InetAddress.getLocalHost().getHostName());
        context.write(STARTER_STR + " Now is " + new Date() + " \r\n");
        context.flush();
    }

    /**
     *
     * @param channelHandlerContext
     * @param request
     * @throws Exception
     *
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String request) throws Exception {
        boolean close = false;
        String response;

        if(StringUtils.isBlank(request)){
            response = STARTER_STR + " Please type worlds not empty!\r\n";
        }else if("bye".equalsIgnoreCase(request)){
            response = STARTER_STR + " Bye !!!\r\n";
            close = true;
        }else{
            response = STARTER_STR + " Reveice message : " + request + " \r\n";
        }

        ChannelFuture future = channelHandlerContext.write(response);
        future.addListener((ChannelFuture f) -> {
            System.out.println("==> " + 1);
        });
        future.addListener((ChannelFuture f) -> {
            System.out.println("==> " + 2);
        });
        if(close){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context){
        context.flush();
    }
    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}