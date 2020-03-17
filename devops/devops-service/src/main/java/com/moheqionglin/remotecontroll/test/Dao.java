package com.moheqionglin.remotecontroll.test;

import org.springframework.stereotype.Component;

/**
 * @ClassName : Dao
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:16
 */
@Component
public class Dao {

    private String database = "default db";

    public void doBiz() {
        System.out.println("dao do biz <" + database + ">");
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}