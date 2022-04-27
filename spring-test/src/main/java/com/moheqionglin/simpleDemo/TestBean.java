package com.moheqionglin.simpleDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author wanli zhou
 * @created 2018-06-24 8:29 PM.
 */
@Component
public class TestBean {
    private String name ;

    @Autowired
    private TestDao testDao;

    @Autowired
    private List<AbstractT> ats;

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

    public List<AbstractT> getAts() {
        return ats;
    }
}

