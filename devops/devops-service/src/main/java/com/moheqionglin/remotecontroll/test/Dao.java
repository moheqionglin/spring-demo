package com.moheqionglin.remotecontroll.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName : Dao
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:16
 */
@Component
public class Dao {

    @Value("${devops.conf:default db}")
    private String database;

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