package com.moheqionglin.beanpostprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-03 12:41
 */
@Component
public class ZhuRen2 {
    @Autowired
    private ZhuRen zhuren;

    public ZhuRen2() {
        System.out.println("ZhuRen2 构造器 " + zhuren.getAnimal().getName());
    }

    public ZhuRen getZhuren() {
        return zhuren;
    }
}