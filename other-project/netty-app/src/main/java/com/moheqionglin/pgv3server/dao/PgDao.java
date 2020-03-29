package com.moheqionglin.pgv3server.dao;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    private final List<String> selectColus = Arrays.asList("int_t", "varchar_t", "bool_t", "char_t",
            "short_t", "long_t", "data_t", "float_t", "double_t");
    private final HashMap<String, Class> col2JavaTypes = new HashMap<>();
    private List<List<Object>> result = Arrays.asList(
            Arrays.asList(1, "万里-1", false, 'A', (short)1, 1L, new Date(), 1.1f, 1.2d),
            Arrays.asList(2, "万里-2", true, 'B', (short)2, 2L, new Date(), 2.1f, 2.2d),
            Arrays.asList(3, "万里-3", false, 'C', (short)3, 3L, new Date(), 3.1f, 3.2d),
            Arrays.asList(4, "万里-4", true, 'D', (short)4, 4L, new Date(), 4.1f, 4.2d),
            Arrays.asList(5, "万里-5", false, 'E', (short)5, 5L, new Date(), 5.1f, 5.2d),
            Arrays.asList(6, "万里-6", true, 'F', (short)6, 6L, new Date(), 6.1f, 6.2d)
    );

    private final static PgDao pgDao = new PgDao();
    public static PgDao getInstance(){
        return pgDao;
    }
    private PgDao(){init();}

    public ResultSet select(String sql){
        return new ResultSet(selectColus, col2JavaTypes, result);
    }
    private void init() {
        col2JavaTypes.put("int_t", Integer.class);
        col2JavaTypes.put("varchar_t", String.class);
        col2JavaTypes.put("bool_t", Boolean.class);
        col2JavaTypes.put("char_t", Character.class);
        col2JavaTypes.put("short_t", Short.class);
        col2JavaTypes.put("long_t", Long.class);
        col2JavaTypes.put("data_t", Date.class);
        col2JavaTypes.put("float_t", Float.class);
        col2JavaTypes.put("double_t", Double.class);
    }
    public static class ResultSet{
        private List<String> selectColus;
        private HashMap<String, Class> col2JavaTypes;
        private List<List<Object>> result;

        public ResultSet(List<String> selectColus, HashMap<String, Class> col2JavaTypes, List<List<Object>> result) {
            this.selectColus = selectColus;
            this.col2JavaTypes = col2JavaTypes;
            this.result = result;
        }

        public List<String> getSelectColus() {
            return selectColus;
        }

        public HashMap<String, Class> getCol2JavaTypes() {
            return col2JavaTypes;
        }

        public List<List<Object>> getResult() {
            return result;
        }
    }

}