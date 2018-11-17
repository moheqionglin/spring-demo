package com.moheqionglin.telnetDemo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
  //打印读取到的数据
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
      System.err.println(msg);
  }
  //异常数据捕获
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      cause.printStackTrace();
      ctx.close();
  }
}