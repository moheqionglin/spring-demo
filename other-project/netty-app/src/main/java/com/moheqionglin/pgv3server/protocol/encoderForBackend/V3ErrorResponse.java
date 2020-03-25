package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang.StringUtils;
import org.postgresql.util.ServerErrorMessage;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName : ErrorResponse
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 14:32
 *
 * Byte1('E') Identifies the message as an error.
 * + Int32 Length of message contents in bytes, including self.
 *              The message body consists of one or more identified fields, followed by a zero byte as a terminator. Fields can appear in any order. For each field there is the following:
 * + Byte1 A code identifying the field type; if zero, this is the message terminator and no string follows. The presently defined field types are listed in Section 49.6. Since more field types might be added in future, frontends should silently ignore fields of unrecognized type.
 * + String The field value.
 */
public class V3ErrorResponse implements BaseProtocolEncoder {
    private byte flag = 'E';
    private final String START = "SERROR\u0000C42703\u0000M";
    private String errorMxg;

    public V3ErrorResponse(String errorMxg) {
        this.errorMxg = START + errorMxg;
    }

    public V3ErrorResponse(Throwable e) {
        this.errorMxg = START + readExpMsg(e, 100);
    }
    public V3ErrorResponse(String errorMxg, Throwable e) {
        this.errorMxg = START + errorMxg + readExpMsg(e, 100);
    }

    /**
     *   int beresp = pgStream.receiveChar(); 'E'
     *   int l_elen = pgStream.receiveInteger4();
     *   ServerErrorMessage errorMsg =
     *       new ServerErrorMessage(pgStream.receiveErrorString(l_elen - 4));
     * @param buffer
     */
    @Override
    public void encode(ByteBuf buffer) {
        byte[] body  = this.errorMxg.getBytes(CharsetUtil.UTF_8);
        int length = body.length + 4;
        buffer.writeByte(flag);
        buffer.writeInt(length);
        buffer.writeBytes(body);
    }

    private String readExpMsg(Throwable e, int maxLength){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return StringUtils.substring(sw.toString(), 0, maxLength);

    }
}