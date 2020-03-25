package com.moheqionglin.pgv3server.bizservice;

import com.moheqionglin.pgv3server.protocol.BaseProtocolDecoder;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @ClassName : SqlProcessorManager
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 20:02
 */
public class SqlProcessorManager {
    private static List<SqlProcessor> sqlProcessors = new ArrayList();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final static SqlProcessorManager sqlProcessorManager = new SqlProcessorManager();

    private SqlProcessorManager(){
//        Reflections reflections = new Reflections("com.moheqionglin.pgv3server.bizservice");
//        Set<Class<? extends SqlProcessor>> processors = reflections.getSubTypesOf(SqlProcessor.class);
        Set<Class<? extends SqlProcessor>> processors = new HashSet<>();
        processors.add(DDLSqlProcessor.class);
        processors.add(InsertSqlProcessor.class);
        processors.add(NavicatSqlProcessor.class);
        processors.add(SelectSqlProcessor.class);

        for(Iterator<Class<? extends SqlProcessor>> iterator = processors.iterator(); iterator.hasNext();) {
            Class<? extends SqlProcessor> clazz = iterator.next();
            try {
                sqlProcessors.add((SqlProcessor) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("init class " + clazz.getName() + " error", e);
            }
        }
    }

    public static SqlProcessorManager getInstance(){
        return sqlProcessorManager;
    }

    public List<SqlProcessor> getSqlProcessors() {
        return sqlProcessors;
    }
}