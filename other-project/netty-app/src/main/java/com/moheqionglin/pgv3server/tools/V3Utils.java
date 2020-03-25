package com.moheqionglin.pgv3server.tools;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.Charsets;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @ClassName : V3Utils
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 14:36
 */
public class V3Utils {
    public static String getRemoteIp(ChannelHandlerContext ctx) {
        InetSocketAddress isa = (InetSocketAddress) ctx.channel().remoteAddress();
        InetAddress address = isa.getAddress();
        return address.getHostAddress();
    }

    /**
     * 默认 \0结尾分割
     * @param buf
     * @return
     */
    public static String readV3String(ByteBuf buf) {
        int len = buf.bytesBefore((byte) 0);
        byte[] body = new byte[len];
        buf.readBytes(body);
        buf.readByte();// skip '\0'
        return new String(body, Charsets.UTF_8);
    }
}