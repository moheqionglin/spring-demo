package com.moheqionglin.pgv3server.dao;

import java.util.HashMap;

/**
 * @ClassName : PgDao
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 15:53
 */
public class PgDao {
    public static final HashMap<String, String> user2Pwd = new HashMap<>();
    public static final HashMap<String, String> pgConnectionParams = new HashMap<>();

    static {
        user2Pwd.putIfAbsent("willy", "123456#");
        user2Pwd.putIfAbsent("willy2", "1234567#");

        pgConnectionParams.put("client_encoding", "UTF8");
        pgConnectionParams.put("application_name", "PostgreSQL JDBC Driver");
        pgConnectionParams.put("IntervalStyle", "postgres");
        pgConnectionParams.put("DateStyle", "ISO, YMD");
        pgConnectionParams.put("integer_datetimes", "on");
        pgConnectionParams.put("server_encoding", "UTF8");
        pgConnectionParams.put("is_superuser", "off");
        pgConnectionParams.put("standard_conforming_strings", "on");
        pgConnectionParams.put("server_version", "9.4.18");
        pgConnectionParams.put("TimeZone", "Asia/Shanghai");
    }

}