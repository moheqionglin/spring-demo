package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import io.netty.buffer.ByteBuf;
import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @ClassName : V3Query
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 17:39
 *
 * Byte1('Q') Identifies the message as a simple query.
 * + Int32 Length of message contents in bytes, including self.
 * + String The query string itself.
 *
 */
public class V3Query implements BaseProtocolDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String query;
    @Override
    public void decode(ByteBuf byteBuf) {
        byteBuf.readByte();
        int len = byteBuf.readInt();
        byte[] body = new byte[len - 4];
        byteBuf.readBytes(body);
        byteBuf.readByte();// skip '\0'
        this.query = new String(body, Charsets.UTF_8);
        log.info("decode query success, query = {}", query);
    }

    @Override
    public byte getFlag() {
        return 'Q';
    }

    public String getQuery() {
        return query;
    }
}