package com.moheqionglin.pgv3server.bizservice;

import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3CommandComplete;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ErrorResponse;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : DDLSqlProcessor
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 19:39
 */
public class DDLSqlProcessor implements SqlProcessor{
    public static final String CREATE_SCHEMA_SQL = "[cC][rR][eE][aA][tT][eE]\\s+[sS][cC][hH][eE][mM][aA]\\s+(\\w+|'{1}\\w+'{1})";
    public static final String DROP_SCHEMA_SQL = "[dD][rR][oO][pP]\\s+[sS][Cc][Hh][Ee][Mm][Aa]\\s+(\\w+|'{1}\\w+'{1})";
    public static final String CREATE_TABLE_SQL = "[cC][rR][eE][aA][tT][eE]\\s+[tT][Aa][Bb][Ll][Ee][\\s\\S]+";
    public static final String DROP_TABLE_SQL = "[dD][rR][oO][pP]\\s+[tT][Aa][Bb][Ll][Ee]\\s+[\\w.]+";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(ChannelHandlerContext ctx, String sql) {
        sql = sql.replaceAll( "\n", " ");
        String resultMsg = null;
        try {

            if (sql.matches(CREATE_SCHEMA_SQL)) {
                String schemaName = sql.replaceAll("[cC][rR][eE][aA][tT][eE]\\s+[sS][cC][hH][eE][mM][aA]\\s+", "");
                schemaName = StringUtils.trimToEmpty(StringUtils.trim(schemaName));
                resultMsg = "CREATE SCHEMA";
            } else if (sql.matches(DROP_SCHEMA_SQL)) {
                String schemaName = sql.replaceAll("[dD][rR][oO][pP]\\s+[sS][Cc][Hh][Ee][Mm][Aa]\\s+", "");
                schemaName = StringUtils.trimToEmpty(StringUtils.trim(schemaName));

                resultMsg = "DROP SCHEMA";
            } else if (sql.matches(CREATE_TABLE_SQL)) {
                String tableName = sql.replaceAll("[cC][rR][eE][aA][tT][eE]\\s+[tT][Aa][Bb][Ll][Ee]", "");
                tableName = StringUtils.trimToEmpty(StringUtils.trim(tableName));

                resultMsg = "CREATE TABLE";
            } else if (sql.matches(DROP_TABLE_SQL)) {
                String tableName = sql.replaceAll("[cC][rR][eE][aA][tT][eE]\\s+[tT][Aa][Bb][Ll][Ee]", "");
                tableName = StringUtils.trimToEmpty(StringUtils.trim(tableName));
                resultMsg = "DROP TABLE";
            }
            log.info("process success for sql {}", sql);
        }catch (Exception e){
            ctx.writeAndFlush(new V3ErrorResponse("ddl error ", e));
            log.info("process error for sql " + sql, e);
        }

        if(resultMsg != null){
            ctx.writeAndFlush(new V3CommandComplete(resultMsg));
        }else{
            ctx.writeAndFlush(new V3ErrorResponse("upsupport sql "));
            log.info("upsupport sql " + sql);
        }
    }

    @Override
    public boolean accept(String sql) {
        return sql.matches(CREATE_SCHEMA_SQL) ||
                sql.matches(DROP_SCHEMA_SQL) ||
                sql.matches(CREATE_TABLE_SQL) ||
                sql.matches(DROP_TABLE_SQL) ;
    }
}