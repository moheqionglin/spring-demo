package com.moheqionglin.circleDependence.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-12 10:55
 */
@Component
public class Bean2 {

    @Autowired
    private Bean1 bean1;

    public Bean2() {
        System.out.println("bean1 init, bean1 " + bean1);
    }

    public String biz() {
        System.out.println("bean1 invoke bean1.biz = " + bean1.biz());
        return "BEAN2";
    }
}