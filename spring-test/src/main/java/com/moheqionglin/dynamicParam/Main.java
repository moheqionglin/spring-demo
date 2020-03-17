package com.moheqionglin.dynamicParam;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 15:34
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        DubboService dubboService = context.getBean(DubboService.class);

    }


    @Configuration
    @ComponentScan("com.moheqionglin.dynamicParam")
    class Config{

    }
}