package com.moheqionglin.geomesa;

import org.geotools.data.*;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.locationtech.geomesa.hbase.data.HBaseDataStoreFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.sort.SortBy;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 15:20
 */
public class GeomesaSearch {

    public static void main(String[] args) throws IOException, CQLException {
        String CQL = "" +
//                "bbox(point_wz,121.497553006667000,31.341626383333300,121.497553060000000,31.341626883333300) AND" +
//                "  bizid = 1 AND " +
                "biztime DURING 2019-11-14T15:03:07.000Z/2019-11-14T15:09:09.000Z";

        GeomesaSearch geomesaSearch = new GeomesaSearch();
        DataStore dataStore = geomesaSearch.getDataStore(GeomesaInit.ZK, GeomesaInit.CATALOG);

        geomesaSearch.query(dataStore,GeomesaInit.getSimpleFeatureTypeName(),CQL);
    }


    public void query(DataStore dataStore, String sft , String CQL) throws CQLException, IOException {
        List<Query> queries = new ArrayList<>();
        queries.add(new Query(sft, ECQL.toFilter(CQL)));

        for (Query query : queries) {
            System.out.println("=== Running query " + ECQL.toCQL(query.getFilter()));
            if (query.getPropertyNames() != null) {
                System.out.println("Returning attributes " + Arrays.asList(query.getPropertyNames()));
            }
            if (query.getSortBy() != null) {
                SortBy sort = query.getSortBy()[0];
                System.out.println("Sorting by " + sort.getPropertyName() + " " + sort.getSortOrder());
            }
            try (FeatureReader<SimpleFeatureType, SimpleFeature> reader = dataStore.getFeatureReader(query, Transaction.AUTO_COMMIT)) {
                int n = 0;
                while (reader.hasNext()) {
                    SimpleFeature feature = reader.next();
                    if (n++ < 10) {
                        // use geotools data utilities to get a printable string
                        System.out.println(String.format("%02d", n) + " " + DataUtilities.encodeFeature(feature));
                    } else if (n == 10) {
                        System.out.println("...");
                    }
                }
                System.out.println();
                System.out.println("Returned " + n + " total features");
                System.out.println();
            }
        }
    }

    public DataStore getDataStore(String zk, String catalog){
        Map<String, Serializable> params= new HashMap<>();
        params.put("hbase.zookeepers",zk);
        params.put("hbase.catalog",catalog);
        DataStore ds = new HBaseDataStoreFactory().createDataStore(params);
        return ds;
    }

}