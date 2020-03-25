package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : V3Bind
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 17:17
 *
 * Byte1('B')  Identifies the message as a Bind command.
 * + Int32  Length of message contents in bytes, including self.
 * + String  The name of the destination portal (an empty string selects the unnamed portal).
 * + String   The name of the source prepared statement (an empty string selects the unnamed prepared statement).
 * + Int16   The number of parameter format codes that follow (denoted C below). This can be zero to indicate that there are no parameters or that the parameters all use the default format (text); or one, in which case the specified format code is applied to all parameters; or it can equal the actual number of parameters.
 * + Int16[C]  The parameter format codes. Each must presently be zero (text) or one (binary).
 * + Int16  The number of parameter values that follow (possibly zero). This must match the number of parameters needed by the query.
 *          Next, the following pair of fields appear for each parameter:
 * + Int32  The length of the parameter value, in bytes (this count does not include itself). Can be zero. As a special case, -1 indicates a NULL parameter value. No value bytes follow in the NULL case.
 * + Byte n  The value of the parameter, in the format indicated by the associated format code. n is the above length.
 *          After the last parameter, the following fields appear:
 * + Int16  The number of result-column format codes that follow (denoted R below). This can be zero to indicate that there are no result columns or that the result columns should all use the default format (text); or one, in which case the specified format code is applied to all result columns (if any); or it can equal the actual number of result columns of the query.
 * + Int16[R]  The result-column format codes. Each must presently be zero (text) or one (binary).
 */
public class V3Bind implements BaseProtocolDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String destinationPortalName;
    private String sourcePreparedStatementName;
    private short parameterNumber;
    private short valuesNumber;
    private int parameterValueLength;
    private byte values[] ;
    private short resultColumnCodesNum;
    private short[] resultColumnCodes;

    @Override
    public void decode(ByteBuf byteBuf) {
        byteBuf.readByte();
        int length = byteBuf.readInt();
        this.destinationPortalName = V3Utils.readV3String(byteBuf);
        this.sourcePreparedStatementName = V3Utils.readV3String(byteBuf);
        this.parameterNumber = byteBuf.readShort();
        this.valuesNumber = byteBuf.readShort();
        this.parameterValueLength = byteBuf.readInt();
        this.values = new byte[parameterValueLength];
        byteBuf.readBytes(this.values);
        this.resultColumnCodesNum = byteBuf.readShort();
        this.resultColumnCodes = new short[resultColumnCodesNum];
        for(int i = 0 ; i < this.resultColumnCodesNum ; i ++){
            this.resultColumnCodes[i] = byteBuf.readShort();
        }

        log.info("decode V3Bind destinationPortalName = {}, sourcePreparedStatementName = {}," +
                "parameterNumber = {}, valuesNumber = {}, parameterValueLength ={}, resultColumnCodesNum = {}",
                destinationPortalName, sourcePreparedStatementName, parameterNumber, valuesNumber, parameterValueLength, resultColumnCodesNum);
    }

    @Override
    public byte getFlag() {
        return 'B';
    }

    public String getDestinationPortalName() {
        return destinationPortalName;
    }

    public String getSourcePreparedStatementName() {
        return sourcePreparedStatementName;
    }

    public short getParameterNumber() {
        return parameterNumber;
    }

    public short getValuesNumber() {
        return valuesNumber;
    }

    public int getParameterValueLength() {
        return parameterValueLength;
    }

    public byte[] getValues() {
        return values;
    }

    public short getResultColumnCodesNum() {
        return resultColumnCodesNum;
    }

    public short[] getResultColumnCodes() {
        return resultColumnCodes;
    }
}