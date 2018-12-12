package com.moheqionglin.dynamicInjectBean;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wanli.zhou
 * @description
 * @time 05/12/2018 4:37 PM
 */
public class DynamicBean {
    @Autowired
    private TestDao testDao;

    private String var;

    public void print(){
        System.out.println("var" + var + " -->Test " + testDao.getName());
    }


    public void init(){
        System.out.println("DynamicBean dao init with var = " + var);
    }

    public void destory(){
        System.out.println("DynamicBean dao destory with var = " + var);
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}