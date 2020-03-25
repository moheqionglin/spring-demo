package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.google.common.base.Charsets;
import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

/**
 * @ClassName : V3CommandComplete
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 21:16
 *
 * Byte1('C') Identifies the message as a command-completed response.
 * + Int32 Length of message contents in bytes, including self.
 * + String The command tag. This is usually a single word that identifies which SQL command was completed.
 *      For an INSERT command, the tag is INSERT oid rows, where rows is the number of rows inserted. oid is the object ID of the inserted row if rows is 1 and the target table has OIDs; otherwise oid is 0.
 *      For a DELETE command, the tag is DELETE rows where rows is the number of rows deleted.
 *      For an UPDATE command, the tag is UPDATE rows where rows is the number of rows updated.
 *      For a SELECT or CREATE TABLE AS command, the tag is SELECT rows where rows is the number of rows retrieved.
 *      For a MOVE command, the tag is MOVE rows where rows is the number of rows the cursor's position has been changed by.
 *      For a FETCH command, the tag is FETCH rows where rows is the number of rows that have been retrieved from the cursor.
 *      For a COPY command, the tag is COPY rows where rows is the number of rows copied. (Note: the row count appears only in PostgreSQL 8.2 and later.)
 */
public class V3CommandComplete implements BaseProtocolEncoder {
    private String result;

    public V3CommandComplete(String result) {
        this.result = result;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte((byte)'C');
        byte body[] = this.result.getBytes(Charsets.UTF_8);
        int length = 5 + body.length;
        buffer.writeInt(length);
        buffer.writeBytes(body);
        buffer.writeByte((byte)0);
    }
}