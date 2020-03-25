package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import com.moheqionglin.pgv3server.tools.PGV3Type;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Byte1('T') Identifies the message as a row description.
 * Int32      Length of message contents in bytes, including self.
 * Int16      Specifies the number of fields in a row (can be zero).
 *               Then, for each field, there is the following:
 * String     The field name.
 * Int32      If the field can be identified as a column of a specific table, the object ID of the table; otherwise zero.
 * Int16      If the field can be identified as a column of a specific table, the attribute number of the column; otherwise zero.
 * Int32      The object ID of the field's data type.
 * Int16      The data type size (see pg_type.typlen). Note that negative values denote variable-width types.
 * Int32      The type modifier (see pg_attribute.atttypmod). The meaning of the modifier is type-specific.
 * Int16      The format code being used for the field. Currently will be zero (text) or one (binary). In a RowDescription returned from the statement variant of Describe, the format code is not yet known and will always be zero.
 */
public class V3RowDescription implements BaseProtocolEncoder {
    private List<String> cols;
    private HashMap<String, Class> col2JavaType = new HashMap();

    private Logger log = LoggerFactory.getLogger(this.getClass());
    public V3RowDescription(List<String> cols, HashMap<String, Class> col2JavaType) {
        this.cols = cols;
        this.col2JavaType = col2JavaType;
    }

    /**
     * @param byteBuf
     * String columnLabel = pgStream.receiveString(); //id
     * int tableOid = pgStream.receiveInteger4(); //12222
     * short positionInTable = (short) pgStream.receiveInteger2(); //1
     * int typeOid = pgStream.receiveInteger4();//23
     * int typeLength = pgStream.receiveInteger2();//4
     * int typeModifier = pgStream.receiveInteger4();//-1
     * int formatType = pgStream.receiveInteger2();//0  0
     */
    @Override
    public void encode(ByteBuf byteBuf) {

        byteBuf.writeByte((byte)'T');
        int length = 6;
        int writeIndex = byteBuf.writerIndex();
        byteBuf.writeInt(length);
        byteBuf.writeShort((short) cols.size());

        for (int i = 0; i < cols.size(); i++) {
            String col = StringUtils.trimToEmpty(cols.get(i));
            PGV3Type pgType = PGV3Type.parse(col2JavaType.get(col));
            byte[] bytes = (col + "\0").getBytes(CharsetUtil.UTF_8);
            byteBuf.writeBytes(bytes);
            byteBuf.writeInt(12399);
            byteBuf.writeShort((short)i);
            byteBuf.writeInt(pgType.getOid());
            byteBuf.writeShort(pgType.getLength());
            byteBuf.writeInt(pgType.getTypeCode());
            byteBuf.writeShort((short)0);
            length += ( 18 + bytes.length);
        }
        byteBuf.setInt(writeIndex, length);
        log.info(" V3RowDescription length = {}, readable bytes = {}", length, byteBuf.readableBytes());
    }
}
