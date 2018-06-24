package com.moheqionglin.services;

/**
 * @author wanli zhou
 * @created 2018-06-24 8:29 PM.
 */
public class TestBean {
    private String name ;

    public void print(){
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

