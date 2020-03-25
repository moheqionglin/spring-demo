package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3BindComplete
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 17:26
 * Byte1('2')  Identifies the message as a Bind-complete indicator.
 *
 * Int32(4)    Length of message contents in bytes, including self.
 */
public class V3BindComplete implements BaseProtocolEncoder {
    private byte flag = '2';
    private int length = 4;
    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(flag);
        buffer.writeInt(length);
    }
}