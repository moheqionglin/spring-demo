package com.moheqionglin.hbase;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wanli.zhou
 * @description
 * @time 01/04/2019 2:38 PM
 */
public class Read {
    public static void main(String[] args) throws IOException {
        Read.read();
    }

    private static void putData() {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        Connection connection = HbaseConnectionUtil.getConnection(Executors.newFixedThreadPool(1));
        final HbaseTemplate hbaseTemplate = new HbaseTemplate(connection);


        hbaseTemplate.insertOrUpdate("t1", new byte[]{0,9,-3,123,-86,-28,42,84, -87,-109,-52, 50,48},
                "info", "d", new byte[]{ 0, 0, 0, 27, 0, 0, 0, 73, 0, 13, 0, 9, -3, 123, -86, -28, 42, 84, -87, -109, -52, 50, 48, 1, 100, 100, 127, -1, -1, -1, -1, -1, -1, -1, 4, 2, 0, 0, 0, 66, 50, -80, 1, 0, 0, 0, 0, 0, 39, 105, 71, 1, 0, 0, 1, 104, 51, 105, 27, -18, 1, 8, 3, 64, 94, 95, -41, -24, -83, -116, 55, 64, 63, 87, 116, -42, -6, -36, 90, 127, -8, 0, 0, 0, 0, 0, 0, 50, 48, 48, -79, 1, 0, 101, 110, 97, 109, 101, 45, 50, -80, 5, 7, 16, 25, 52, 56,58}, System.currentTimeMillis());
    }

    public static void read() throws IOException {

        Connection connection = HbaseConnectionUtil.getConnection(Executors.newFixedThreadPool(1));
        Table htable = connection.getTable(TableName.valueOf("appsdk:event-test"));
        final HbaseTemplate hbaseTemplate = new HbaseTemplate(connection);

        Scan scan = new Scan();
        scan.setCaching(10);

        ResultScanner rs =  htable.getScanner(scan);

        for (Result r : rs) {
            for (KeyValue kv : r.raw()) {
                System.out.println(String.format("row:%s, family:%s, qualifier:%s, qualifiervalue:%s, timestamp:%s.",
                        Bytes.toString(kv.getRow()),
                        Bytes.toString(kv.getFamily()),
                        Bytes.toString(kv.getQualifier()),
                        Bytes.toString(kv.getValue()),
                        kv.getTimestamp()));
            }
        }

        rs.close();

    }
}