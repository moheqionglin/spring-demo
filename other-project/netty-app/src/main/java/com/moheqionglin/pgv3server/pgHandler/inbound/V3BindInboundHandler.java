package com.moheqionglin.pgv3server.pgHandler.inbound;

import com.moheqionglin.pgv3server.bizservice.SqlProcessor;
import com.moheqionglin.pgv3server.bizservice.SqlProcessorManager;
import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.decoderForFront.V3Bind;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3BindComplete;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3EmptyQueryResponse;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ReadyForQuery;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class V3BindInboundHandler extends SimpleChannelInboundHandler<V3Bind> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3Bind msg) throws Exception {

        ctx.writeAndFlush(new V3BindComplete());

        String sql = msg.getSourcePreparedStatementName();

        if(StringUtils.isBlank(sql)){
            log.info("Extend query sql is empty! ");
            ctx.writeAndFlush(new V3EmptyQueryResponse());
            ctx.writeAndFlush(new V3ReadyForQuery((byte) 'I'));
            return;
        }

        List<SqlProcessor> sqlProcessors = SqlProcessorManager.getInstance().getSqlProcessors();

        boolean process = false;
        for(SqlProcessor sqlProcessor : sqlProcessors){
            if(sqlProcessor.accept(sql)){
                sqlProcessor.process(ctx, sql);
                process = true;
                break;
            }
        }
        if(!process){
            log.error("unsupport sql" + sql );
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}
