package com.moheqionglin.dubboSingleTcp;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 11:29
 */
public class Invocation {
    private String className = "";
    private String method = "";
    private Class[] types = new Class[2];

    public Invocation(String className, String method, Class[] types) {
        this.className = className;
        this.method = method;
        this.types = types;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class[] getTypes() {
        return types;
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }
}