package com.moheqionglin.es.dao;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-22 17:06
 */
@Component
public class EsCommonDao {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void insert(String index, String id, JSONObject jsonObject) throws IOException {
        IndexRequest indexRequest = generateIndexReq(index, id, jsonObject);
        IndexResponse resp = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    public void batchInsert(String index, List<JSONObject> jsonObjects) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for(JSONObject jsonObject : jsonObjects){
            IndexRequest indexReq = generateIndexReq(index, jsonObject.get("id").toString(), jsonObject);
            bulkRequest.add(indexReq);
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    private IndexRequest generateIndexReq(String index, String id, JSONObject jsonObject) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);

        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject();
        for(String fieldName : jsonObject.keySet()){
            xContentBuilder.field(fieldName, jsonObject.get(fieldName));
        }
        xContentBuilder.endObject();
        IndexRequest indexReq = indexRequest.id(id)
                .opType(DocWriteRequest.OpType.CREATE)
                .source(xContentBuilder);
        return indexReq;
    }

    public void query(){

    }

}