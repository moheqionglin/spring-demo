package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.exception.MessageIncompleteException;
import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : SSLRequest
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 15:20
 *
 *   Int32(8)         Length of message contents in bytes, including self.
 * + Int32(80877103)  The SSL request code. The value is chosen to contain 1234 in the most significant 16 bits, and 5679 in the least significant 16 bits. (To avoid confusion, this code must not be the same as any protocol version number.)
 */
public class V3SSLRequest implements BaseProtocolDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean accept(ByteBuf byteBuf) throws MessageIncompleteException {
        if(!makeSureReadableBytes(byteBuf, 1)){
            return false;
        }
        byte type = byteBuf.readByte();
        if (type != 0){
            byteBuf.resetReaderIndex();
            return false;
        }
        byteBuf.resetReaderIndex();
        if(!makeSureReadableBytes(byteBuf, 4)){
            throw new MessageIncompleteException();
        }
        int messageLength = byteBuf.readInt();
        if(!makeSureReadableBytes(byteBuf, messageLength - 4)){
            byteBuf.resetReaderIndex();
            throw new MessageIncompleteException();
        }
        int messageFlag = byteBuf.readInt();
        if (messageFlag == 80877103) {
            byteBuf.resetReaderIndex();
            return true;
        }
        byteBuf.resetReaderIndex();
        return false;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        byteBuf.readByte();
        byteBuf.readInt();
        log.info("Decode success V3SSLRequest");
    }

    @Override
    public byte getFlag() {
        return 0;
    }
}