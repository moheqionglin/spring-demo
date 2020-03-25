package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3ParseComplete
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 16:46
 * Byte1('1') Identifies the message as a Parse-complete indicator.
 *
 * Int32(4) Length of message contents in bytes, including self.
 */
public class V3ParseComplete implements BaseProtocolEncoder {
    private byte flag = '1';
    private int length = 4;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(flag);
        buffer.writeInt(length);
    }
}