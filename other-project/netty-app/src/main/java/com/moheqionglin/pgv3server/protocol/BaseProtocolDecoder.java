package com.moheqionglin.pgv3server.protocol;

import com.moheqionglin.pgv3server.exception.MessageIncompleteException;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * @ClassName : BaseProtocolDecoder
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 14:16
 *
 * read message to PG Client
 */
public interface BaseProtocolDecoder {
    /**
     * 去报报文的完整性
     * @param byteBuf
     * @return
     * @throws MessageIncompleteException
     */
    default boolean accept(ByteBuf byteBuf) throws MessageIncompleteException{
        if(!makeSureReadableBytes(byteBuf, 1)){
            return false;
        }
        byte type = byteBuf.readByte();
        if (type != getFlag()){
            byteBuf.resetReaderIndex();
            return false;
        }
        if(!makeSureReadableBytes(byteBuf, 4)){
            throw new MessageIncompleteException();
        }
        int messageLength = byteBuf.readInt();
        if(!makeSureReadableBytes(byteBuf, messageLength - 4)){
            byteBuf.resetReaderIndex();
            throw new MessageIncompleteException();
        }
        byteBuf.resetReaderIndex();
        return true;
    }


    void decode(ByteBuf byteBuf);

    default boolean makeSureReadableBytes(ByteBuf byteBuf, int size) {
        return byteBuf.readableBytes() >= size;
    }

    byte getFlag();
}