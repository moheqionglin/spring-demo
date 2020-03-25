package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3NoticeResponse
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 15:39
 *
 * Byte1('N')   Identifies the message as a notice.
 * Int32        Length of message contents in bytes, including self. The message body consists of one or more identified fields, followed by a zero byte as a terminator. Fields can appear in any order. For each field there is the following:
 * Byte1        A code identifying the field type; if zero, this is the message terminator and no string follows. The presently defined field types are listed in Section 49.6. Since more field types might be added in future, frontends should silently ignore fields of unrecognized type.
 * String       The field value.
 */
public class V3NoticeResponse implements BaseProtocolEncoder {
    private byte flat = 'N';
    private int length;
    private byte identifying;
    private String value;

    public V3NoticeResponse(int length, byte identifying, String value) {
        this.length = length;
        this.identifying = identifying;
        this.value = value;
    }

    @Override
    public void encode(ByteBuf buffer) {

    }
}