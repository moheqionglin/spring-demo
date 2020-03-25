package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import com.moheqionglin.pgv3server.tools.PGV3Type;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.buffer.ByteBuf;
import org.apache.hadoop.hbase.util.Bytes;
import org.mortbay.util.ajax.JSON;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName : V3DataRow
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 22:15
 *
 * Byte1('D')   Identifies the message as a data row.
 * Int32        Length of message contents in bytes, including self.
 * Int16        The number of column values that follow (possibly zero).
 *                  Next, the following pair of fields appear for each column:
 * Int32        The length of the column value, in bytes (this count does not include itself). Can be zero. As a special case, -1 indicates a NULL column value. No value bytes follow in the NULL case.
 * Byten        The value of the column, in the format indicated by the associated format code. n is the above length.
 */
public class V3DataRow implements BaseProtocolEncoder {

    private List<Object> row ;

    public V3DataRow(List<Object> rst) {
        this.row = rst;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte((byte)'D');
        int length = 6;
        int writeIndex = buffer.writerIndex();
        buffer.writeInt(length);

        buffer.writeShort((short) row.size());
        for(int j = 0 ; j < row.size(); j ++){
            byte[] serialize = serialize(row.get(j));
            buffer.writeInt(serialize.length);
            buffer.writeBytes(serialize);
            length += (4 + serialize.length);
        }
        buffer.setIndex(writeIndex, length);
    }

    public static byte[] serialize(Object o){
        if(o instanceof String || o instanceof Long ||
                o instanceof Integer || o instanceof Double
            || o instanceof Float || o instanceof Short ){
            return Bytes.toBytes(o.toString());
        }
        if(o instanceof Boolean){
            Boolean v =  (Boolean) o;
            return (v == null || !v) ? new byte[]{102} : new byte[]{116};
        }
        if(o instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date d =  (Date)o;
            return Bytes.toBytes(sdf.format(d));
        }
        if(o instanceof java.util.Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            java.util.Date d =  (java.util.Date)o;
            return Bytes.toBytes(sdf.format(d));
        }
        if(o instanceof Character){
            Character d = (Character)o;
            return new byte[]{(byte)d.charValue()};
        }
        return new byte[0];
    }

}