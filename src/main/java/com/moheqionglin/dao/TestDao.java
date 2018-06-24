package com.moheqionglin.dao;

import org.springframework.stereotype.Component;

/**
 * @author wanli zhou
 * @created 2018-06-24 9:11 PM.
 */
@Component
public class TestDao {
    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "TestDao{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
