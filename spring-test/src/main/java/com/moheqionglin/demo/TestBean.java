package com.moheqionglin.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * @author wanli zhou
 * @created 2018-06-24 8:29 PM.
 */
@ComponentScan
public class TestBean {
    private String name ;

    @Autowired
    private TestDao testDao;
    public TestBean(){
        System.out.println("TestBean consustor");
    }
    @PostConstruct
    public void print(){
        testDao.getName();
        System.out.println("--->");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "name='" + name + '\'' +
                '}';
    }
}

