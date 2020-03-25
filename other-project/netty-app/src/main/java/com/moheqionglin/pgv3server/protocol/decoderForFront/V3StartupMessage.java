package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.exception.MessageIncompleteException;
import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : V3StartupMessage
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 14:51
 *
 * https://www.postgresql.org/docs/9.4/protocol-message-formats.html
 *   Int32 (Length of message contents in bytes, including self)
 * + Int32(196608)
 * + String
 */
public class V3StartupMessage implements BaseProtocolDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private int messageLength;
    private short majorVersion;
    private short minorVersion;
    private List<String> parameters = new ArrayList<>();
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
        if (messageFlag == 196608) {
            byteBuf.resetReaderIndex();
            return true;
        }
        byteBuf.resetReaderIndex();
        return false;
    }

    /**
     *  pgStream.sendInteger4(length);
     *  pgStream.sendInteger2(3); // protocol major pgStream.sendInteger2(0); // protocol minor
     *  for (byte[] encodedParam : encodedParams) {
     *      pgStream.send(encodedParam);
     *      pgStream.sendChar(0);
     *  }
     * @param byteBuf
     * @throws MessageIncompleteException
     *
     * accept 已经保证了报文的完整性，这里放心用就好
     */
    @Override
    public void decode(ByteBuf byteBuf) {
        this.messageLength = byteBuf.readInt();
        this.majorVersion = byteBuf.readShort();
        this.minorVersion = byteBuf.readShort();

        byte[] body = new byte[this.messageLength - 8];
        byteBuf.readBytes(body);
        this.parameters.add(new String(body, Charset.forName("UTF-8")));
        log.info("Decode success V3StartupMessage, message length = {}, major version = {}, minor Version = {}, parameters = {}"
                , messageLength, majorVersion, minorVersion, parameters);
    }

    @Override
    public byte getFlag() {
        return 0;
    }
}