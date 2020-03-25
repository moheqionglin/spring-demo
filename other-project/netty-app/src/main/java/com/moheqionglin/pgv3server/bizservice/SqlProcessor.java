package com.moheqionglin.pgv3server.bizservice;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName : SqlProcessor
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 19:37
 */
public interface SqlProcessor {
    void process(ChannelHandlerContext ctx, String sql);
    boolean accept(String sql);
}