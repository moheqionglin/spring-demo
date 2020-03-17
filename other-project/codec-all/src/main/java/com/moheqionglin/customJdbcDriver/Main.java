package com.moheqionglin.customJdbcDriver;

import java.sql.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 22:09
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

//    eid, coordinate, altitude, speed, direction, gnsstime, servertime, attributes, geom
//    eid:String:index=true
//    coordinate:String
//    altitude:double
//    speed:double
//    direction:double
//    gnsstime:Date
//    servertime:Date
//    attributes:String
//    *geom:Point:srid=4326";

        Class.forName("com.moheqionglin.customJdbcDriver.GeomesaDriver");
        Connection connection = DriverManager.getConnection("jdbc:tssql://10.16.224.28:2181/hbase?back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx");
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("insert into test.point(eid, coordinate, altitude, speed, direction, gnsstime, servertime, attributes, geom) " +
                "values (200001, '4326', 23.67, 65.0, 156, 1575526165000, 15ß75526165000, '{\"key\":\"value\"}, POINT(123.45, 34.56)')");

    }
}