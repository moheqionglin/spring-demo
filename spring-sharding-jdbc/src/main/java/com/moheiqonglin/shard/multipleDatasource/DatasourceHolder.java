package com.moheiqonglin.shard.multipleDatasource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:06 PM
 */
public class DatasourceHolder {

    private final static ThreadLocal<String> datasourceKey = new ThreadLocal<>();

    private final static Set<String> datasoureKeys = Collections.synchronizedSet(new HashSet<String>());

    static {
        datasoureKeys.add("product1");
        datasoureKeys.add("product2");
    }
    public static void setDatasourceKey(String key){
        if(!datasoureKeys.contains(key)){
            throw new RuntimeException("NO datasource for " + key);
        }
        datasourceKey.set(key);
        System.out.println("=> DatasourceHolder : set " + key);
    }

    public static String getDatasourceKey(){
        System.out.println("=> DatasourceHolder : get " + datasourceKey.get());
        return datasourceKey.get();
    }

    public static void clearDatasourceKey(){
        System.out.println("=> DatasourceHolder : remove " + datasourceKey.get());
        datasourceKey.remove();
    }

    public static void addDsKeys(String product){
        datasoureKeys.add(product);
    }
}