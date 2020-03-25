package com.moheqionglin.pgv3server.protocol.encoderForBackend;

import com.moheqionglin.pgv3server.protocol.BaseProtocolEncoder;
import io.netty.buffer.ByteBuf;
import org.apache.commons.io.Charsets;

import java.util.HashMap;

/**
 * @ClassName : V3ParameterStatus
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 16:12
 *
 * Byte1('S')  Identifies the message as a run-time parameter status report.
 * Int32       Length of message contents in bytes, including self.
 * String      The name of the run-time parameter being reported.
 * String      The current value of the parameter.
 */
public class V3ParameterStatus implements BaseProtocolEncoder {
    private byte flag = 'S';
    private HashMap<String, String> parameters = new HashMap<>();

    public V3ParameterStatus(HashMap<String, String> parameters) {
        this.parameters.putAll(parameters);
    }

    /**
     * @param buffer
     *      int l_len = pgStream.receiveInteger4();
     *      String name = pgStream.receiveString(); //int len = pg_input.scanCStringLength();
     *      String value = pgStream.receiveString();
     */
    @Override
    public void encode(ByteBuf buffer) {
        //循环多次写入, 每个参数写一次
        for(String key : this.parameters.keySet()){
            buffer.writeByte(flag);
            byte[] paramName = (key+ "\0").getBytes(Charsets.UTF_8);
            byte[] paramValue = (this.parameters.get(key) + "\0").getBytes(Charsets.UTF_8);
            int totalLength = 4 + paramName.length + paramValue.length;
            buffer.writeInt(totalLength);
            buffer.writeBytes(paramName);
            buffer.writeBytes(paramValue);
        }
    }
}