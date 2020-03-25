package com.moheqionglin.pgv3server.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @ClassName : BaseProtocolCodec
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-24 23:02
 *
 * send message to PG Client
 */
public interface BaseProtocolEncoder<T> {
    void encode(ByteBuf buffer);
}