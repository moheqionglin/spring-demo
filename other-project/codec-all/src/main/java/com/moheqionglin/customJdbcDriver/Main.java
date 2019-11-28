package com.moheqionglin.customJdbcDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 22:09
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.moheqionglin.customJdbcDriver.GeomesaDriver");
        Connection connection = DriverManager.getConnection("jdbc:tssql://10.16.224.28:2181/hbase?back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx");
    }
}