package com.moheqionglin.pgv3server.nettyserver;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class SimpleQueryDMLTest {
    private final String PG_SERVER_URL = "jdbc:postgresql://127.0.0.1:5432/willy?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedInserts=true&loglevel=2&rewriteBatchedStatements=true&preferQueryMode=simple";
    private final String PG_NETTY_URL = "jdbc:postgresql://127.0.0.1:6543/willy?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedInserts=true&loglevel=2&rewriteBatchedStatements=true&preferQueryMode=simple";

    private final String PG_LOCAL_URL = PG_NETTY_URL;
    private final String PG_USER = "willy";
    private final String PG_PWD = "123456#";


    @Test
    public void connectionTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement statement = con.createStatement();
        System.out.println("> get connection < ");
        con.close();
    }

    @Test
    public void createTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        stm.execute("insert into demo(id, name, sex,createdAt) values (1, 'willy-mock-1', 1, '2019-02-03 12:34:50')");
    }
    @Test
    public void deleteTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        stm.execute("delete from demo where id = 1");
    }

    @Test
    public void updateTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        stm.executeUpdate("update demo set name = 'change' where id = 1");
    }


    @Test
    public void selectTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();

        ResultSet rst = stm.executeQuery("select id, name, sex,createdAt from demo");
        while (rst.next()){
            System.out.println(rst.getInt("int_t") + "\t" +
                    rst.getString("varchar_t") +"\t"+
                    rst.getBoolean("bool_t") +"\t"+
                    rst.getShort("short_t") +"\t"+
                    rst.getLong("long_t") +"\t"+
                    rst.getFloat("float_t") +"\t"+
                    rst.getDouble("double_t") +"\t"+
                    sdf.format(rst.getDate("data_t"))
            );
        }
    }


    @Test
    public void selectCountTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery("select count(1) from demo");
        while (rst.next()){
            System.out.println(rst.getString("count")
            );
        }
    }

    @Test
    public void insertExceptionTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        boolean re = stm.execute("insert into demo(id, name) values (1, 'mock-1')");
        System.out.println(re);
    }

    @Test
    public void selectExceptionTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();
        boolean re = stm.execute("select id from demo where col = 11");
        System.out.println(re);
    }

    @Test
    public void getColumnsTest() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(PG_LOCAL_URL, PG_USER, PG_PWD);
        Statement stm = con.createStatement();

        ResultSet resultSet = stm.executeQuery("select * from demo");
        List<String> colTypes = new ArrayList<>();
        List<String> colNames = new ArrayList<>();
        List<String> colClassNames = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            colNames.add(metaData.getColumnName(i));
        }
        for (int i = 1; i <= columnCount; i++) {
            colClassNames.add(metaData.getColumnClassName(i));
            colTypes.add(metaData.getColumnTypeName(i));
        }

        System.out.println(colNames);
        System.out.println("------------------");
        System.out.println(colTypes);
        System.out.println("------------------");
        System.out.println(colClassNames);
    }
}