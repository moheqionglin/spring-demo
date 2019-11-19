package com.moheqionglin.customJdbcDriver;

import java.util.Properties;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-11 13:47
 */
public class Utils {
    public static final String PREFIX = "jdbc:tssql:";
    public static final int TS_MAJOR_VERSION = 2;
    public static final int TS_MINOR_VERSION = 1;
    public static final String URL_PARAM_BACK_ZK_HOST = "back_zk_host";
    public static final String URL_PARAM_CATALOG = "catalog";
    public static String getLoggingInfo(){
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        return element.getClassName()+"."+element.getMethodName()+" ["+element.getLineNumber()+"]";
    }

    public static Properties defaultProps() {
        return new Properties();
    }
}