package com.moheqionglin.geomesa;

import org.geotools.data.DataStore;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureWriter;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.identity.FeatureIdImpl;
import org.locationtech.geomesa.hbase.data.HBaseDataStoreFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 14:55
 */
public class GeomesaWrit {

    AtomicInteger id = new AtomicInteger(1);

    public static void main(String[] args) throws IOException {
        GeomesaWrit geomesaWrit = new GeomesaWrit();
        DataStore dataStore = geomesaWrit.getDataStore(GeomesaDDL.ZK, GeomesaDDL.CATALOG);
        geomesaWrit.write(dataStore, "lbs", geomesaWrit.getDatas());
    }

    public DataStore getDataStore(String zk, String catalog){
        Map<String, Serializable> params= new HashMap<>();
        params.put("hbase.zookeepers",zk);
        params.put("hbase.catalog",catalog);
        DataStore ds = new HBaseDataStoreFactory().createDataStore(params);
       return ds;
    }

    public void write(DataStore ds, String stfName, List<LbsBean> datas) throws IOException {

        SimpleFeatureType sft = ds.getSchema(stfName);

        try(SimpleFeatureWriter writer= (SimpleFeatureWriter)ds.getFeatureWriterAppend(stfName, Transaction.AUTO_COMMIT)){
            for(LbsBean data : datas){
                //   *geom:Point:srid=4326
                SimpleFeatureBuilder builder = new SimpleFeatureBuilder(sft);
                builder.set("bizid", data.getBizId() + "");
                builder.set("bizfield1", data.getBizfield1());
                builder.set("biztime", new Date(data.getBiztime()));
                builder.set("bizpoint","POINT(" + data.getLon() + " " + data.getLat() + ")");

                SimpleFeature simpleFeature = builder.buildFeature(id.incrementAndGet() + "");

                SimpleFeature toWrite = writer.next();
                toWrite.setAttributes(simpleFeature.getAttributes());
                ((FeatureIdImpl) toWrite.getIdentifier()).setID(simpleFeature.getID());
                toWrite.getUserData().put(Hints.USE_PROVIDED_FID, Boolean.TRUE);
                toWrite.getUserData().putAll(simpleFeature.getUserData());
                try {
                    writer.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LbsBean> getDatas(){
        List<LbsBean> datas = new ArrayList<>();
        datas.add(new LbsBean("1", "A", 1573715167977L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "B", 1573715167978L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "C", 1573715167979L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "D", 1573715167980L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "E", 1573715167981L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "F", 1573715167982L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "G", 1573715167983L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "H", 1573715167984L, 117.092327478167000d,36.740326090000000d));

        datas.add(new LbsBean("1", "I", 1573715168977L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "J", 1573715169978L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "K", 1573715167979L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "L", 1573715167980L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "M", 1573715167981L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "N", 1573715167982L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "O", 1573715167983L, 117.092327478167000d,36.740326090000000d));
        datas.add(new LbsBean("1", "P", 1573715167984L, 117.092327478167000d,36.740326090000000d));
        return datas;
    }
}