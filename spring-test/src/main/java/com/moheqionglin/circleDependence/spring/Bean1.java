package com.moheqionglin.circleDependence.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-12 10:55
 */
@Component
public class Bean1 {
    @Autowired
    private Bean2 bean2;

    public Bean1() {
        System.out.println("bean1 init, bean2 " + bean2);
    }

    public String biz(){
        System.out.println("bean1 invoke bean2.biz = " + bean2.biz());
        return "BEAN1";
    }
}