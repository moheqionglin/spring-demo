package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3ReadyForQuery
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 16:24
 * Byte1('Z')  Identifies the message type. ReadyForQuery is sent whenever the backend is ready for a new query cycle.
 *
 * Int32(5)   Length of message contents in bytes, including self.
 *
 * Byte1    Current backend transaction status indicator. Possible values are 'I' if idle (not in a transaction block); 'T' if in a transaction block; or 'E' if in a failed transaction block (queries will be rejected until block is ended).
 */
public class V3ReadyForQuery implements BaseProtocolEncoder {
    private byte flag = 'Z';
    private int length = 5;
    private byte status;

    public V3ReadyForQuery(byte status) {
        this.status = status;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(flag);
        buffer.writeInt(length);
        buffer.writeByte(status);
    }
}