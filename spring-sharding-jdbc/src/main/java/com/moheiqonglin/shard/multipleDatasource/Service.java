package com.moheiqonglin.shard.multipleDatasource;

import com.moheiqonglin.shard.multipleDatasource.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:36 PM
 */
@Component
public class Service {

    @Autowired
    private Dao dao;

    public void doSomething(String product){
//        dao.tst();
        dao.queryAllUsers(product);
    }

    public void doSomething(String product, String bbb){
        dao.queryAllUsers(product, bbb);
    }
}