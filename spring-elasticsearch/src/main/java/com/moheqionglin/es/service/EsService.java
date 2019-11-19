package com.moheqionglin.es.service;

import com.alibaba.fastjson.JSONObject;
import com.moheqionglin.es.dao.EsCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-22 18:45
 */
@Component
public class EsService {
    private List<JSONObject> data1 = new ArrayList<>();

    private List<JSONObject> data2 = new ArrayList<>();

    private int max = 1000;

    @Autowired
    private EsCommonDao esCommonDao;


    public synchronized void batchCreateData1(JSONObject jsonObject){
        data1.add(jsonObject);
        if(data1.size() > max){
            List old = data1;
            data1 = new ArrayList<>();
            try {
                esCommonDao.batchInsert("mohe-point", old);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void batchCreateData2(JSONObject jsonObject){
        data2.add(jsonObject);
        if(data2.size() > max){
            List old = data2;
            data2 = new ArrayList<>();
            try {
                esCommonDao.batchInsert("caster-point", old);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}