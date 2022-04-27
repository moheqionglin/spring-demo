package com.shard.mysql;


import com.google.common.io.ByteStreams;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;

public class MysqlConnTest {
    @Test
    public void queryMysql() throws ClassNotFoundException, SQLException {

        // jdbc:mysql://10.231.128.30:6446/plat_order
        Class.forName("com.mysql.jdbc.Driver");
        //jdbc:mysql://10.231.128.30:6446/plat_order jdbc:mysql://47.100.203.236:3306/yz
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/yz", "root","Mohe123#");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()){
            System.out.println(resultSet.getObject(1));
        }

    }

    @Test
    public void hexTest(){
        byte[] bs = new byte[]{(byte)10,(byte)56,(byte)46,(byte)48,(byte)46,(byte)50,(byte)50,(byte)0,(byte)116,(byte)12,(byte)0,(byte)0,(byte)37,(byte)97,(byte)61,(byte)33,(byte)91,(byte)84,(byte)8,(byte)31,(byte)0,(byte)-1,(byte)-1,(byte)-1,(byte)2,(byte)0,(byte)-1,(byte)-57,(byte)21,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)65,(byte)23,(byte)38,(byte)54,(byte)19,(byte)94,(byte)121,(byte)75,(byte)21,(byte)110,(byte)69,(byte)116,(byte)0,(byte)99,(byte)97,(byte)99,(byte)104,(byte)105,(byte)110,(byte)103,(byte)95,(byte)115,(byte)104,(byte)97,(byte)50,(byte)95,(byte)112,(byte)97,(byte)115,(byte)115,(byte)119,(byte)111,(byte)114,(byte)100,(byte)0};
        for(byte b : bs){
            System.out.print("0x"+Integer.toHexString(b) + " ");
        }
    }
}
