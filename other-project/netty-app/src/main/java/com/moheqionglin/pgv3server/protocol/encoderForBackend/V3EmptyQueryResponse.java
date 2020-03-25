package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3EmptyQueryResponse
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 17:31
 * Byte1('I') Identifies the message as a response to an empty query string. (This substitutes for CommandComplete.)
 *
 * Int32(4) Length of message contents in bytes, including self.
 */
public class V3EmptyQueryResponse implements BaseProtocolEncoder {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte((byte)'I');
        buffer.writeInt(4);
    }
}