package com.moheqionglin.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 21/12/2018 11:08 AM
 */
@Component
public class TestBean {

    @Autowired
    private PrototypeBean prototypeBean;
    @Autowired
    private SingletonBean singletonBean;


    public void print(){
        prototypeBean.print();
        singletonBean.print();
    }
}