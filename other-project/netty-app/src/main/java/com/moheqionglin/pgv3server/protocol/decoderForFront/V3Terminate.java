package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : V3Terminate
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 17:10
 * Byte1('X')  Identifies the message as a termination.
 * Int32(4)  Length of message contents in bytes, including self.
 *
 */
public class V3Terminate implements BaseProtocolDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void decode(ByteBuf byteBuf) {
        byteBuf.readByte();
        byteBuf.readInt();
        log.info("decode V3Terminate success");
    }

    @Override
    public byte getFlag() {
        return 'X';
    }
}