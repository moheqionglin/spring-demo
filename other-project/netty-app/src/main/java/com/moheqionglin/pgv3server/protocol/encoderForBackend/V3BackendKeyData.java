package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName : V3BackendKeyData
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 16:20
 */
public class V3BackendKeyData  implements BaseProtocolEncoder {
    private byte flag = 'K';
    private int length = 12;
    private int pid;
    private int ckey;

    public V3BackendKeyData(int pid, int ckey) {
        this.pid = pid;
        this.ckey = ckey;
    }

    /**
     *    int l_len = pgStream.receiveInteger4();
     *    int l_msgLen = pgStream.receiveInteger4(); //12
     *    if (l_msgLen != 12) {
     *         throw new PSQLException(GT.tr("Protocol error.  Session setup failed."),
     *        PSQLState.PROTOCOL_VIOLATION);
     *    }
     *    int pid = pgStream.receiveInteger4();
     *    int ckey = pgStream.receiveInteger4();
     * @param buffer
     */
    @Override
    public void encode(ByteBuf buffer) {

        buffer.writeByte(flag);
        buffer.writeInt(length);
        buffer.writeInt(pid);
        buffer.writeInt(ckey);
    }
}