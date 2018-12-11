package com.moheqionglin.aop.reflection;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 9:57 PM
 */
public class Parent{
    private String parentVar;

    @Override
    public String toString() {
        return "parent {" +
                "parentVar='" + parentVar + '\'' +
                '}';
    }
}