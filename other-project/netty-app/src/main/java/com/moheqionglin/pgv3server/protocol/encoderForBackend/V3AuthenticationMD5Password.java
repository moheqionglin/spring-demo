package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * @ClassName : AuthenticationMD5Password
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 15:28
 *
 *  Byte1('R')  Identifies the message as an authentication request.
 * + Int32(12)  Length of message contents in bytes, including self.
 * + Int32(5)   Specifies that an MD5-encrypted password is required.
 * + Byte4 The  salt to use when encrypting the password.
 */
public class V3AuthenticationMD5Password implements BaseProtocolEncoder {
    private byte flag = 'R';
    private int length = 12;
    private int required = 5;
    private int md5Salt = -1;

    public V3AuthenticationMD5Password(int md5Salt) {
        this.md5Salt = md5Salt;
    }

    /**
     * pgStream.receiveChar();
     * int l_msgLen = pgStream.receiveInteger4();
     * int areq = pgStream.receiveInteger4();  5
     * byte[] md5Salt = pgStream.receive(4);
     * @param buffer
     * @throws IOException
     */
    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(this.flag);
        buffer.writeInt(length);
        buffer.writeInt(required);
        buffer.writeInt(md5Salt);
    }
}