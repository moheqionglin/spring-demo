package com.moheqionglin.aop.warnning3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:03 PM
 */
@Component
public class SimpleBean1 {

    @Autowired
    private SimpleBean simpleBean;

    public void print(){
        System.out.println(simpleBean.print());
    }
}
