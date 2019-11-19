package com.moheqionglin.customJdbcDriver;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 22:09
 */
public class GeomesaDriver implements Driver {

    private static GeomesaDriver registeredDriver;
    static {
        try {
            register();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     *
     * jdbc:tssql://zk_host:port/zk_path?back_zk_host=xxx&catalog=xxx
     *   eg:
     *  jdbc:tssql://10.16.224.28:2181/hbase?back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx
     *
     * params.put("hbase.catalog", catalog);
     *         params.put("hbase.zookeepers", hbaseZK);
     *
     * */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        Object[] objects = parseURL(url, info);
        String zk = objects[0] + ":" + objects[1] + "," + info.get(Utils.URL_PARAM_BACK_ZK_HOST) + "/" + objects[2];
        return new GeomesaConnection(zk, info.getProperty(Utils.URL_PARAM_CATALOG));
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if(!url.startsWith(Utils.PREFIX)){
            return false;
        }
        try {
            URI uri = new URI(url.substring(5));
            if(uri.getHost() == null){
                throw new SQLException("Invalid URL, no host specified");
            }
            if(uri.getPath() == null){
                throw new SQLException("Invalid URL, no index specified");
            }
            if(uri.getPath().split("/").length > 2){
                throw new SQLException("Invalid URL, "+uri.getPath()+" is not a valid index");
            }

        } catch (URISyntaxException e) {
            throw new SQLException("Unable to parse URL", e);
        }
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        Properties props = (Properties)parseURL(url, info)[3];
        DriverPropertyInfo[] result = new DriverPropertyInfo[props.size()];
        int index = 0;
        for(Map.Entry<Object,Object> entry : props.entrySet()){
            result[index] = new DriverPropertyInfo((String)entry.getKey(), entry.getValue().toString());
            index++;
        }
        return result;
    }


    private Object[] parseURL(String url, Properties info) throws SQLException{
        if(!acceptsURL(url)){
            throw new SQLException("Invalid url");
        }
        try {

            URI uri = new URI(url.substring(11));
            String host = uri.getHost();
            if(uri.getPort() < 0){
                throw new RuntimeException("invalid port");
            }
            int port = uri.getPort();

            String zk_path = uri.getPath().length() <= 1 ? null : uri.getPath().split("/")[1];
            Properties props = Utils.defaultProps();
            if(info != null) {
                props.putAll(info);
            }
            info = props;
            if(uri.getQuery() != null){
                for(String keyValue : uri.getQuery().split("&")){
                    String[] parts = keyValue.split("=");
                    if(parts.length > 1){
                        info.setProperty(parts[0].trim(), parts[1].trim());
                    }
                }
            }

            return new Object[]{host, port, zk_path, info};
        } catch (URISyntaxException e) {
            throw new SQLException("Unable to parse URL. Pleas use '"+Utils.PREFIX+"//host:port/schema?{0,1}(param=value&)*'", e);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new SQLException("No shema (index) specified. Pleas use '"+Utils.PREFIX+"//host:port/schema?{0,1}(param=value&)*'");
        }catch(Exception e){
            throw new SQLException("Unable to connect to database due to: "+e.getClass().getName(), e);
        }
    }

    @Override
    public int getMajorVersion() {
        return Utils.TS_MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return Utils.TS_MINOR_VERSION;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException(Utils.getLoggingInfo());
    }


    private static void register() throws SQLException {
        if (isRegistered()) {
            throw new IllegalStateException(
                    "Driver is already registered. It can only be registered once.");
        }
        GeomesaDriver registeredDriver = new GeomesaDriver();
        DriverManager.registerDriver(registeredDriver);
        GeomesaDriver.registeredDriver = registeredDriver;
    }

    public static boolean isRegistered() {
        return registeredDriver != null;
    }
}