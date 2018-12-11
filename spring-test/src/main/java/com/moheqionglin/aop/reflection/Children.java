package com.moheqionglin.aop.reflection;


/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 9:57 PM
 */
public class Children extends Parent {
    private String childrenVar;

    String defaultVar;
    protected String protectedVar;
    public String publicVar;

    @Override
    public String toString() {

        return super.toString() + "\n Children{" +
                "childrenVar='" + childrenVar + '\'' +
                '}';
    }
}