package com.moheqionglin.remotecontroll.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @ClassName : Service
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:16
 */
@Component
public class Service {

    private HashMap<String, String> cache = new HashMap<>();

    @Autowired
    private Dao dao;
    public void biz(){
        System.out.println(cache);
        dao.doBiz();
    }

    public HashMap<String, String> getCache() {
        return cache;
    }

    public void setCache(HashMap<String, String> cache) {
        this.cache = cache;
    }
}