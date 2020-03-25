package com.moheqionglin.pgv3server.bizservice;

import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3CommandComplete;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ErrorResponse;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : InsertSqlProcessor
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 19:41
 */
public class InsertSqlProcessor implements SqlProcessor{

    private static String INSERT_SQL = "insert into";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(ChannelHandlerContext ctx, String sql) {
        try{
            //process sql
            ctx.writeAndFlush(new V3CommandComplete("INSERT 0 " + sql.substring(sql.toUpperCase().indexOf("VALUES")).length()));
            log.info("Simple Query, insert success for sql {}", sql);
        }catch (Exception e){
            ctx.writeAndFlush(new V3ErrorResponse("insert error ", e));
            log.error("Simple Query, insert error for sql " + sql, e);
        }
    }

    @Override
    public boolean accept(String sql) {
        return sql.startsWith(INSERT_SQL);
    }
}