package com.moheqionglin.circleDependence.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-12 10:54
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Bean1 bean1 = context.getBean(Bean1.class);
        Bean2 bean2 = context.getBean(Bean2.class);

        System.out.println(bean1.biz());
        System.out.println("---------");
        System.out.println(bean2.biz());
    }

    @Configuration
    @ComponentScan("com.moheqionglin.circleDependence.spring")
    static class Config{

    }
}