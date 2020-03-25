package com.moheqionglin.pgv3server.tools;

import org.postgresql.core.Oid;

import java.util.HashMap;

/**
 * @ClassName : PGV3Type
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 21:32
 *
 * TypeInfoCache
 * private static final Object[][] types = {
 *       {"int2", Oid.INT2, Types.SMALLINT, "java.lang.Integer", Oid.INT2_ARRAY},
 *       {"int4", Oid.INT4, Types.INTEGER, "java.lang.Integer", Oid.INT4_ARRAY},
 *       {"oid", Oid.OID, Types.BIGINT, "java.lang.Long", Oid.OID_ARRAY},
 *       {"int8", Oid.INT8, Types.BIGINT, "java.lang.Long", Oid.INT8_ARRAY},
 *       {"money", Oid.MONEY, Types.DOUBLE, "java.lang.Double", Oid.MONEY_ARRAY},
 *       {"numeric", Oid.NUMERIC, Types.NUMERIC, "java.math.BigDecimal", Oid.NUMERIC_ARRAY},
 *       {"float4", Oid.FLOAT4, Types.REAL, "java.lang.Float", Oid.FLOAT4_ARRAY},
 *       {"float8", Oid.FLOAT8, Types.DOUBLE, "java.lang.Double", Oid.FLOAT8_ARRAY},
 *       {"char", Oid.CHAR, Types.CHAR, "java.lang.String", Oid.CHAR_ARRAY},
 *       {"bpchar", Oid.BPCHAR, Types.CHAR, "java.lang.String", Oid.BPCHAR_ARRAY},
 *       {"varchar", Oid.VARCHAR, Types.VARCHAR, "java.lang.String", Oid.VARCHAR_ARRAY},
 *       {"text", Oid.TEXT, Types.VARCHAR, "java.lang.String", Oid.TEXT_ARRAY},
 *       {"name", Oid.NAME, Types.VARCHAR, "java.lang.String", Oid.NAME_ARRAY},
 *       {"bytea", Oid.BYTEA, Types.BINARY, "[B", Oid.BYTEA_ARRAY},
 *       {"bool", Oid.BOOL, Types.BIT, "java.lang.Boolean", Oid.BOOL_ARRAY},
 *       {"bit", Oid.BIT, Types.BIT, "java.lang.Boolean", Oid.BIT_ARRAY},
 *       {"date", Oid.DATE, Types.DATE, "java.sql.Date", Oid.DATE_ARRAY},
 *       {"time", Oid.TIME, Types.TIME, "java.sql.Time", Oid.TIME_ARRAY},
 *       {"timetz", Oid.TIMETZ, Types.TIME, "java.sql.Time", Oid.TIMETZ_ARRAY},
 *       {"timestamp", Oid.TIMESTAMP, Types.TIMESTAMP, "java.sql.Timestamp", Oid.TIMESTAMP_ARRAY},
 *       {"timestamptz", Oid.TIMESTAMPTZ, Types.TIMESTAMP, "java.sql.Timestamp",
 *           Oid.TIMESTAMPTZ_ARRAY},
 *       //JCP! if mvn.project.property.postgresql.jdbc.spec >= "JDBC4.2"
 *       {"refcursor", Oid.REF_CURSOR, Types.REF_CURSOR, "java.sql.ResultSet", Oid.REF_CURSOR_ARRAY},
 *       //JCP! endif
 *       {"json", Oid.JSON, Types.OTHER, "org.postgresql.util.PGobject", Oid.JSON_ARRAY},
 *       {"point", Oid.POINT, Types.OTHER, "org.postgresql.geometric.PGpoint", Oid.POINT_ARRAY}
 *   };
 */
public enum PGV3Type {
    BOOLEAN(Boolean.class, Oid.BOOL, (short)1, -1),
    SHORT(Short.class, Oid.INT2, (short)2, -1),
    CHAR(Character.class, Oid.CHAR, (short)1, -1),
    INTEGER(Integer.class, Oid.INT4, (short)4, -1),
    LONG(Long.class, Oid.INT8, (short)8, -1),
    FLOAT(Float.class, Oid.FLOAT4, (short)4, -1),
    DOUBLE(Double.class, Oid.NUMERIC, (short)8, 655366),
    STRING(String.class, Oid.VARCHAR, (short)65535, 14),
    DATE(java.util.Date.class, Oid.TIMESTAMP,  (short)8,-1)
    ;

    private PGV3Type(Class clazz, int oid, short length, int typeCode) {
        this.clazz = clazz;
        this.oid = oid;
        this.length = length;
        this.typeCode = typeCode;
    }

    private Class clazz;
    private int oid;
    private short length;
    private int typeCode;
    private static HashMap<Class, PGV3Type> c2p = new HashMap<>();
    static {
        c2p.put(Boolean.class, BOOLEAN);
        c2p.put(Short.class, SHORT);
        c2p.put(Character.class, CHAR);
        c2p.put(Integer.class, INTEGER);
        c2p.put(Long.class, LONG);
        c2p.put(Float.class, FLOAT);
        c2p.put(Double.class, DOUBLE);
        c2p.put(String.class, STRING);
        c2p.put(java.util.Date.class, DATE);
    }
    public int getOid() {
        return oid;
    }

    public short getLength() {
        return length;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public static PGV3Type parse(Class clazz) {
        return c2p.getOrDefault(clazz, null);
    }
}