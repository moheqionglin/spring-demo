package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.exception.MessageIncompleteException;
import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : V3PasswordMessage
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 15:46
 * Byte1('p')       Identifies the message as a password response. Note that this is also used for GSSAPI and SSPI response messages (which is really a design error, since the contained data is not a null-terminated string in that case, but can be arbitrary binary data).
 * Int32            Length of message contents in bytes, including self.
 * String           The password (encrypted, if requested).
 */
public class V3PasswordMessage implements BaseProtocolDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private byte[] digest = null;

    /**
     *         MD5(MD5(password + user) + salt)
     *         byte[] digest = MD5Digest.encode(user.getBytes("UTF-8"), password.getBytes("UTF-8"), md5Salt);
     *         pgStream.sendChar('p');
     *         pgStream.sendInteger4(4 + digest.length + 1);
     *         pgStream.send(digest);
     *         pgStream.sendChar(0);
     *         pgStream.flush();
     * @param byteBuf
     */
    @Override
    public void decode(ByteBuf byteBuf) {
        byteBuf.readByte();
        int messageLength = byteBuf.readInt();
        byte[] digest = new byte[messageLength - 5];
        byteBuf.readBytes(digest);
        //discard one byte 0
        byteBuf.readByte();
        log.info("Decode success V3PasswordMessage");
    }

    @Override
    public byte getFlag() {
        return 'p';
    }

    public byte[] getDigest() {
        return digest;
    }
}