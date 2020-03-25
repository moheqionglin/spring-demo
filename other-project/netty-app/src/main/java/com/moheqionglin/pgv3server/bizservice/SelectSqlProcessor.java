package com.moheqionglin.pgv3server.bizservice;

import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3CommandComplete;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3DataRow;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3ErrorResponse;
import com.moheqionglin.pgv3server.protocol.encoderForBackend.V3RowDescription;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final List<String> selectColus = Arrays.asList("int_t", "varchar_t", "bool_t", "char_t",
            "short_t", "long_t", "data_t", "float_t", "double_t");
    private final HashMap<String, Class> col2JavaTypes = new HashMap<>();
    private List<List<Object>> result = Arrays.asList(
            Arrays.asList(1, "万里-1", false, 'A', (short)1, 1L, new Date(), 1.1f, 1.2d),
            Arrays.asList(2, "万里-2", true, 'B', (short)2, 2L, new Date(), 2.1f, 2.2d),
            Arrays.asList(3, "万里-3", false, 'C', (short)3, 3L, new Date(), 3.1f, 3.2d),
            Arrays.asList(4, "万里-4", true, 'D', (short)4, 4L, new Date(), 4.1f, 4.2d),
            Arrays.asList(5, "万里-5", false, 'E', (short)5, 5L, new Date(), 5.1f, 5.2d),
            Arrays.asList(6, "万里-6", true, 'F', (short)6, 6L, new Date(), 6.1f, 6.2d)
    );

    public SelectSqlProcessor(){
        init();
    }

    @Override
    public void process(ChannelHandlerContext ctx, String sql) {
        try{
            //process select
            ctx.writeAndFlush(new V3RowDescription(selectColus, col2JavaTypes));
            ctx.writeAndFlush(new V3DataRow(result));
            ctx.writeAndFlush(new V3CommandComplete("SELECT " + result.size()));
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

    private void init() {
        col2JavaTypes.put("int_t", Integer.class);
        col2JavaTypes.put("varchar_t", String.class);
        col2JavaTypes.put("bool_t", Boolean.class);
        col2JavaTypes.put("char_t", Character.class);
        col2JavaTypes.put("short_t", Short.class);
        col2JavaTypes.put("long_t", Long.class);
        col2JavaTypes.put("data_t", Date.class);
        col2JavaTypes.put("float_t", Float.class);
        col2JavaTypes.put("double_t", Double.class);
    }

}