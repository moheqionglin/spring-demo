package com.moheqionglin.centerconfig.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : Main
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 19:29
 */
public class Main {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "dev");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Service service = context.getBean(Service.class);
        service.biz();
    }
}