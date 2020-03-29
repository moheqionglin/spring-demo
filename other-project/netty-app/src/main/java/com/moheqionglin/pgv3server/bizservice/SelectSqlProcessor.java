package com.moheqionglin.pgv3server.bizservice;

import com.moheqionglin.pgv3server.dao.PgDao;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3CommandComplete;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3DataRow;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ErrorResponse;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3RowDescription;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.Charsets;
import org.postgresql.core.Oid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.events.Characters;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @ClassName : SelectSqlProcessor
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 19:41
 */
public class SelectSqlProcessor implements SqlProcessor{
    private String select = "select";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PgDao pgDao = PgDao.getInstance();

    @Override
    public void process(ChannelHandlerContext ctx, String sql) {
        try{
            PgDao.ResultSet rst = pgDao.select(sql);
            //process select
            ctx.writeAndFlush(new V3RowDescription(rst.getSelectColus(), rst.getCol2JavaTypes()));
            rst.getResult().stream().forEach(val -> {
                ctx.writeAndFlush(new V3DataRow(val));
            });
            ctx.writeAndFlush(new V3CommandComplete("SELECT " + rst.getResult().size()));

            log.info("Simple Query, select success for sql {}", sql);
        }catch (Exception e){
            ctx.writeAndFlush(new V3ErrorResponse("select error ", e));
            log.error("Simple Query, select error for sql " + sql, e);
        }
    }

    @Override
    public boolean accept(String sql) {
        return sql.toLowerCase().trim().startsWith(select);
    }



}