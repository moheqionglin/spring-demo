package com.moheqionglin.geomesa;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 14:50
 */
public class GeomesaInit {

    public static final String ZK = "10.16.217.67:2181,10.16.217.68:2181,10.16.217.51:2181/hbase";
    public static final String DEFAULT_POINT_SFT = "bizid:String:index=true,bizfield1:String,biztime:Date,*bizpoint:Point:srid=4326";
    public static final String CATALOG = "biz:biz";

    public void createSchema(){
        Map<String, String> params= new HashMap<>();
        params.put("hbase.zookeepers", ZK);
        params.put("hbase.catalog",CATALOG);
        DataStore ds = null;
        try{
            ds = DataStoreFinder.getDataStore(params);
            SimpleFeatureType simpleFeatureType = getSimpleFeatureType();
            ds.createSchema(simpleFeatureType);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public SimpleFeatureType getSimpleFeatureType() {
        SimpleFeatureType sft = null;
        if (sft == null) {
            sft = org.locationtech.geomesa.utils.interop.SimpleFeatureTypes.createType(getSimpleFeatureTypeName(), DEFAULT_POINT_SFT);
            sft.getUserData().put(org.locationtech.geomesa.utils.interop.SimpleFeatureTypes.DEFAULT_DATE_KEY, "biztime");
            sft.getUserData().put("geomesa.table.compression.enabled", "true");
            sft.getUserData().put("geomesa.table.compression.type", "snappy");
            sft.getUserData().put("geomesa.indices.enabled", "id,z2,z3,attr");
            sft.getUserData().put("geomesa.z3.interval", "day");
            sft.getUserData().put("geomesa.z.splits", "2");
            sft.getUserData().put("geomesa.attr.splits", "2");
//            sft.getUserData().put("table.splitter.options",
//                    "attr.eid.pattern:[0-9][05]");
        }
        return sft;
    }

    public static String getSimpleFeatureTypeName(){
        return "lbs";
    }

    public static void main(String[] args) {
        GeomesaInit init = new GeomesaInit();
        init.createSchema();
    }
}