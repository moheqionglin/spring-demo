package com.moheqionglin.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author wanli.zhou
 * @description
 * @time 04/03/2019 10:01 AM
 */
public class HbaseConnectionUtil {

    private static Connection connectionWithPool = null;
    private static Connection connection = null;

    private static Configuration hbaseConfiguration = null;


    public static Configuration generateConfiuration() {
        hbaseConfiguration = HBaseConfiguration.create();
        hbaseConfiguration.set("hbase.zookeeper.property.clientPort", "2181");
//        hbaseConfiguration.set("hbase.zookeeper.quorum", "127.0.0.1");
        hbaseConfiguration.set("hbase.zookeeper.quorum", "172.16.217.51:2181/hbase");
//        hbaseConfiguration.set("hbase.master", "172.16.218.97:8202");
        return hbaseConfiguration;
    }

    public static Connection getConnection(ExecutorService pool){
        generateConfiuration();
        if(connectionWithPool == null || connectionWithPool.isClosed()){
            try {
                connectionWithPool = ConnectionFactory.createConnection(hbaseConfiguration, pool);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connectionWithPool;
    }

    public static Connection getConnectionInstance(ExecutorService pool){
        generateConfiuration();
        try {
            return ConnectionFactory.createConnection(hbaseConfiguration, pool);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        generateConfiuration();
        if(connection == null || connection.isClosed()){
            try {
                connection = ConnectionFactory.createConnection(hbaseConfiguration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void close(){
        hbaseConfiguration.clear();
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
            if(connectionWithPool != null && !connectionWithPool.isClosed()){
                connectionWithPool.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}