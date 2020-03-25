package com.moheqionglin.pgv3server.pgHandler.inbound;

import com.google.common.base.Charsets;
import com.moheqionglin.pgv3server.dao.PgDao;
import com.moheqionglin.pgv3server.exception.GlobalExceptionProcessor;
import com.moheqionglin.pgv3server.protocol.decoderForFront.V3PasswordMessage;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.*;
import com.moheqionglin.pgv3server.tools.PgSessionManager;
import com.moheqionglin.pgv3server.tools.V3Utils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.postgresql.util.MD5Digest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

public class V3PasswordMessageInboundHandler extends SimpleChannelInboundHandler<V3PasswordMessage> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3PasswordMessage message) throws Exception {
        String md5SaltStr = PgSessionManager.getSessionVal(ctx.channel().id(), PgSessionManager.MD5_SALT, null);
        if(StringUtils.isEmpty(md5SaltStr) || !StringUtils.isNumeric(md5SaltStr)){
            throw new Exception("Unexpected ERROR, md5salt is empty or invalid");
        }

        int md5Salt = Integer.valueOf(md5SaltStr);
        boolean authSuccess = false;
        for(String username : PgDao.user2Pwd.keySet()){
            byte[] pwdMd5 = MD5Digest.encode(username.getBytes(Charsets.UTF_8), PgDao.user2Pwd.get(username).getBytes(Charsets.UTF_8), Bytes.toBytes(md5Salt));
            if(Arrays.equals(message.getDigest(), pwdMd5)){
                ctx.writeAndFlush(new V3AuthenticationOk());
                PgSessionManager.setSessionVal(ctx.channel().id(), "userName", username);


                HashMap<String, String> ps = new HashMap<>();
                ps.putAll(PgDao.pgConnectionParams);
                ps.put("session_authorization", username);
                ctx.writeAndFlush(new V3ParameterStatus(ps));

                ctx.writeAndFlush(new V3BackendKeyData(ctx.channel().id().asShortText().hashCode(), ctx.channel().id().asLongText().hashCode()));

                ctx.writeAndFlush(new V3ReadyForQuery((byte) 'I'));
                authSuccess = true;
            }
        }
        if(!authSuccess){
            ctx.writeAndFlush(new V3ErrorResponse("invalid username or password !"));
            log.warn("invalid user name and password from ip {}",  V3Utils.getRemoteIp(ctx));
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        GlobalExceptionProcessor.processException(ctx, cause);
    }
}