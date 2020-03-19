package com.moheqionglin.hbase.test;

import com.moheqionglin.hbase.HbaseConnectionUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * @ClassName : HbaseAdmin
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 11:30
 */
public class HbaseAdminTest {


    public static void main(String[] args) {
        changeTTL();
    }


    public static void changeTTL(){
        Connection connection = HbaseConnectionUtil.getConnection();
        try {
            Admin hBaseAdmin = connection.getAdmin();
            TableName tableName = TableName.valueOf("caster:locationx_point_5fsql_attr_eid_geom_gnsstime__v8");
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("d".getBytes());
            hColumnDescriptor.setTimeToLive(5);
            hTableDescriptor.addFamily(hColumnDescriptor);
            hBaseAdmin.modifyTable(tableName, hTableDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}