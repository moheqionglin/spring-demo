package com.moheqionglin.customJdbcDriver;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-11 19:32
 */
public class GeomesaDriverTeset {
    private GeomesaDriver geomesaDriver = new GeomesaDriver();



    @Test
    public void uriTest() throws URISyntaxException {
        String url = "jdbc:tssql://10.16.224.28:2181/hbase?back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx";

        URI uri = new URI(url.substring(11));
        Assert.assertEquals(uri.getHost(), "10.16.224.28");
        Assert.assertEquals(uri.getPath(), "/hbase");

        URI uri1 = new URI(url.substring(5));
        Assert.assertEquals(uri1.getHost(), "10.16.224.28");
        Assert.assertEquals(uri1.getPath(), "/hbase");
        Assert.assertEquals(uri1.getQuery(), "back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx");

        URI uri2 = new URI("//10.16.224.28:2181/hbase");
        Assert.assertTrue(uri2.getQuery() == null);
    }

    @Test
    public void parseURLTest(){
        String url = "jdbc:tssql://10.16.224.28:2181/hbase?back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx";

        Object[] os = ReflectionTestUtils.invokeMethod(geomesaDriver, "parseURL", url, new Properties());

        for(Object o : os){
            System.out.println(o);
        }
    }

    @Test
    public void getPropertyInfoTest() throws SQLException {
        String url = "jdbc:tssql://10.16.224.28:2181/hbase?back_zk_host=10.16.224.29:2181,10.16.224.30:2181&catalog=xxx";

        DriverPropertyInfo[] propertyInfos = geomesaDriver.getPropertyInfo(url, new Properties());
        for(DriverPropertyInfo driverPropertyInfo : propertyInfos){
            System.out.println(driverPropertyInfo.name + " = " + driverPropertyInfo.value);
        }

    }
}