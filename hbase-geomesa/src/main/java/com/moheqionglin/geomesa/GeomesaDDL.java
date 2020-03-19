package com.moheqionglin.geomesa;

import com.moheqionglin.hbase.HbaseConnectionUtil;
import com.moheqionglin.hbase.HbaseTemplate;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.util.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 14:50
 *
 *
 *  * RDS          Database    table                row           col                  table schema info
 *  * ES           index       type                 document      field                mapping
 *  * Hbase        namespace   table               【 hbase 是 K-V存储，没有row概念 】     自己维护
 *  * geomesa      DataStore   SimpleFeatureType    SimpleFeature  attributes          SimpleFeatureType获取
 *  *
 */

public class GeomesaDDL {

    public static final String ZK = "10.16.217.67:2181,10.16.217.68:2181,10.16.217.51:2181/hbase";
    public static final String DEFAULT_POINT_SFT = "bizid:String:index=true,bizfield1:String,biztime:Date,*bizpoint:Point:srid=4326";
    public static final String CATALOG = "willy:biz";

    public static void main(String[] args) {
        //创建geomesa表
        GeomesaDDL geomesaInit = new GeomesaDDL();
        geomesaInit.createSchema("lbs");

//        geomesaInit.updateSchema("lbs");
//        geomesaInit.updateTTL("lbs", 1);
//        geomesaInit.deleteSchema("lbs");
    }

    /**
     * 创建schema
     */
    public void createSchema(String typeName){
        Map<String, String> params= new HashMap<>();
        params.put("hbase.zookeepers", ZK);
        params.put("hbase.catalog",CATALOG);
        DataStore ds = null;
        try{
            ds = DataStoreFinder.getDataStore(params);
            SimpleFeatureType simpleFeatureType = generateSimpleFeatureType(typeName);
            ds.createSchema(simpleFeatureType);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 删除schema
     */
    public void deleteSchema(String typeName){
        Map<String, String> params= new HashMap<>();
        params.put("hbase.zookeepers", ZK);
        params.put("hbase.catalog",CATALOG);
        DataStore ds = null;
        try{
            ds = DataStoreFinder.getDataStore(params);
            ds.removeSchema(typeName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的schema
     */
    public List<String> listSchemas(String typeName){
        Map<String, String> params= new HashMap<>();
        params.put("hbase.zookeepers", ZK);
        params.put("hbase.catalog",CATALOG);
        DataStore ds = null;
        try{
            ds = DataStoreFinder.getDataStore(params);
            return Arrays.asList(ds.getTypeNames());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * update schema
     * @param typeName
     * @return
     */
    public void updateSchema(String typeName){
        Map<String, String> params= new HashMap<>();
        params.put("hbase.zookeepers", ZK);
        params.put("hbase.catalog",CATALOG);
        DataStore ds = null;
        try{
            ds = DataStoreFinder.getDataStore(params);
            SimpleFeatureType sft = org.locationtech.geomesa.utils.interop.SimpleFeatureTypes.createType(typeName, DEFAULT_POINT_SFT);
            sft.getUserData().put(org.locationtech.geomesa.utils.interop.SimpleFeatureTypes.DEFAULT_DATE_KEY, "biztime");
            sft.getUserData().put("geomesa.table.compression.enabled", "true");
            sft.getUserData().put("geomesa.table.compression.type", "snappy");
            sft.getUserData().put("geomesa.indices.enabled", "id,attr");
            sft.getUserData().put("geomesa.z3.interval", "day");
            sft.getUserData().put("geomesa.z.splits", "2");
            sft.getUserData().put("geomesa.attr.splits", "3");
            sft.getUserData().put("table.splitter.options",
                    "attr.bizid.pattern:[0-9][05]");

            ds.updateSchema(typeName, sft);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTTL(String typeName, int ttl){
        List<String> hbaseTable = new ArrayList<>();

        Connection connection = HbaseConnectionUtil.getConnection();
        try(Table table = connection.getTable(TableName.valueOf(CATALOG))){
            Scan scan = new Scan();
            scan.addColumn("m".getBytes(), "v".getBytes());

            scan.setStartRow(typeName.getBytes());

            char[] chars = typeName.toCharArray();
            chars[chars.length - 1] = (char)(chars[chars.length - 1] + 1);
            scan.setStopRow(new String(chars).getBytes());
            ResultScanner scanner = table.getScanner(scan);

            for (Result result : scanner){
                byte[] value = result.getValue("m".getBytes(), "v".getBytes());
                String tableName = new String(value);
                if(tableName.startsWith(CATALOG)){
                    hbaseTable.add(tableName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(hbaseTable);
        try {
            Admin hBaseAdmin = connection.getAdmin();
            for(String table : hbaseTable){
                TableName t = TableName.valueOf(table);
                HTableDescriptor hTableDescriptor = new HTableDescriptor(t);
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("d".getBytes());
                hColumnDescriptor.setTimeToLive(ttl);
                hTableDescriptor.addFamily(hColumnDescriptor);
                hBaseAdmin.modifyTable(t, hTableDescriptor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SimpleFeatureType generateSimpleFeatureType(String typeName) {
        SimpleFeatureType sft = null;
        sft = org.locationtech.geomesa.utils.interop.SimpleFeatureTypes.createType(typeName, DEFAULT_POINT_SFT);
        sft.getUserData().put(org.locationtech.geomesa.utils.interop.SimpleFeatureTypes.DEFAULT_DATE_KEY, "biztime");
        sft.getUserData().put("geomesa.table.compression.enabled", "true");
        sft.getUserData().put("geomesa.table.compression.type", "snappy");
        sft.getUserData().put("geomesa.indices.enabled", "id,z2,z3,attr");
        sft.getUserData().put("geomesa.z3.interval", "day");
        sft.getUserData().put("geomesa.z.splits", "1");
        sft.getUserData().put("geomesa.attr.splits", "2");
        sft.getUserData().put("table.splitter.options",
                    "attr.bizid.pattern:[0-9][05]");
        return sft;
    }

}