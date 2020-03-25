package com.moheqionglin.pgv3server.protocol.decoderForFront;

import com.moheqionglin.pgv3server.exception.MessageIncompleteException;
import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : V3Parse
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 16:48
 *
 * Byte1('P')  Identifies the message as a Parse command.
 *
 * Int32  Length of message contents in bytes, including self.
 * + String  The name of the destination prepared statement (an empty string selects the unnamed prepared statement).
 * + String  The query string to be parsed.
 * + Int16  The number of parameter data types specified (can be zero). Note that this is not an indication of the number of parameters that might appear in the query string, only the number that the frontend wants to prespecify types for.
 *      Then, for each parameter, there is the following:
 * +  Int32 Specifies the object ID of the parameter data type. Placing a zero here is equivalent to leaving the type unspecified.
 */
public class V3Parse implements BaseProtocolDecoder {
    private String preparedStatement;
    private String query;
    private int parameterNum;
    private List<Integer> OIDS = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     *
     * @param byteBuf
     * Total size = 4 (size field)
     *         + N + 1 (statement name, zero-terminated)
     *         + N + 1 (query, zero terminated)
     *         + 2 (parameter count) + N * 4 (parameter types)
     * int encodedSize = 4
     *         +【 (encodedStatementName == null ? 0 : encodedStatementName.length) + 1】
     *             + queryUtf8.length + 1
     *         + 2 + 4 * params.getParameterCount();
     *
     *     pgStream.sendChar('P'); // Parse
     *     pgStream.sendInteger4(encodedSize);
     *
     *     if (encodedStatementName != null) {
     *     pgStream.send(encodedStatementName);
     * }
     *     pgStream.sendChar(0); // End of statement name
     *     pgStream.send(queryUtf8); // Query string
     *     pgStream.sendChar(0); // End of query string.
     *
     *     pgStream.sendInteger2(params.getParameterCount()); // 【0】# of parameter types specified
     *     for (int i = 1; i <= params.getParameterCount(); ++i) {//
     *     pgStream.sendInteger4(params.getTypeOID(i));
     * }
     */
    @Override
    public void decode(ByteBuf byteBuf) {
        byteBuf.readByte();
        byteBuf.readInt();

        preparedStatement = V3Utils.readV3String(byteBuf);
        query = V3Utils.readV3String(byteBuf);
        parameterNum = byteBuf.readShort();
        for (int i = 0; i < parameterNum; i++) {
            OIDS.add(byteBuf.readInt());
        }
        log.info("decode for V3Parse preparedStatement = {}, query = {}, parameterNum = {}, OIDS = {}",
                preparedStatement,  query, parameterNum, OIDS);
    }

    @Override
    public byte getFlag() {
        return 'P';
    }

}