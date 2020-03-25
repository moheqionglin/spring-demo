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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        Reflections reflections = new Reflections("com.moheqionglin.pgv3server.bizservice");
        Set<Class<? extends SqlProcessor>> processors = reflections.getSubTypesOf(SqlProcessor.class);

        for(Iterator<Class<? extends SqlProcessor>> iterator = processors.iterator(); iterator.hasNext();) {
            Class<? extends SqlProcessor> clazz = iterator.next();
            try {
                sqlProcessors.add((SqlProcessor) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("init class " + clazz.getName() + " error", e);
            }
        }
    }

    public static void main(String[] args) {

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addUrls(ClasspathHelper.forPackage("com.moheqionglin.pgv3server.bizservice"));
        builder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner(),
                new MethodAnnotationsScanner(), new FieldAnnotationsScanner());
        builder.filterInputsBy(new FilterBuilder().includePackage("com.moheqionglin.pgv3server.bizservice"));
        Reflections reflections = new Reflections(builder);

        Set<Class<?extends SqlProcessor>> classes = reflections.getSubTypesOf(SqlProcessor.class);

        for(Class<?> clazz : classes){
            System.out.println(clazz.getName());
        }
//
//        Set<Class<? extends SqlProcessor>> processors = reflections.getSubTypesOf(SqlProcessor.class);
//
//        for(Iterator<Class<? extends SqlProcessor>> iterator = processors.iterator(); iterator.hasNext();) {
//            Class<? extends SqlProcessor> clazz = iterator.next();
//            try {
//                sqlProcessors.add((SqlProcessor) clazz.newInstance());
//            } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static SqlProcessorManager getInstance(){
        return sqlProcessorManager;
    }

    public List<SqlProcessor> getSqlProcessors() {
        return sqlProcessors;
    }
}