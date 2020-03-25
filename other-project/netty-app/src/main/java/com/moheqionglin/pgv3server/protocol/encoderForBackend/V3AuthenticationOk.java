package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3AuthenticationOk
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 16:06
 *
 * Byte1('R')      Identifies the message as an authentication request.
 * Int32(8)        Length of message contents in bytes, including self.
 * Int32(0)        Specifies that the authentication was successful.
 *
 */
public class V3AuthenticationOk implements BaseProtocolEncoder {
    private byte flag = 'R';
    private int length = 8;
    private int authentication = 0;

    /**
     * case R
     * int l_msgLen = pgStream.receiveInteger4();
     * int areq = pgStream.receiveInteger4();
     * @param buffer
     */
    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(flag);
        buffer.writeInt(length);
        buffer.writeInt(authentication);
    }
}