package com.moheqionglin.pgv3server.bizservice;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName : NavicatSqlProcessor
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 19:40
 */
public class NavicatSqlProcessor implements SqlProcessor{

    @Override
    public void process(ChannelHandlerContext ctx, String sql) {

    }

    @Override
    public boolean accept(String sql) {
        return false;
    }
}