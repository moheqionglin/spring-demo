package com.moheqionglin.pgv3server.nettyserver;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SimpleQueryDDLTest {

    private final String PG_SERVER_URL = "jdbc:postgresql://127.0.0.1:5432/willy?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedInserts=true&loglevel=2&rewriteBatchedStatements=true&preferQueryMode=simple";
    private final String PG_NETTY_URL = "jdbc:postgresql://127.0.0.1:6543/willy?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedInserts=true&loglevel=2&rewriteBatchedStatements=true&preferQueryMode=simple";

    private final String PG_LOCAL_URL = PG_NETTY_URL;
    private final String PG_USER = "willy";
    private final String PG_PWD = "123456#";


    @Test
    public void createSchemaTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        String sql = "create schema willy_test" ;
        boolean execute = stm.execute(sql);
        System.out.println("create schema rst: " + execute);

    }
    @Test
    public void dropSchemaTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        String sql = "drop schema willy_test" ;
        boolean execute = stm.execute(sql);
        System.out.println("drop schema rst: " + execute);

    }
    @Test
    public void createTableTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        String sql = "create table demo_test(id int, name varchar, sex bit,createdAt timestamp)" ;
        boolean execute = stm.execute(sql);
        System.out.println("create table rst: " + execute);
    }

    @Test
    public void dropTableTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        String sql = "drop table willy.point" ;
        boolean execute = stm.execute(sql);
        System.out.println("drop table rst: " + execute);
    }
}