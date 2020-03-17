package com.moheqionglin.remotecontroller.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : Main
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:15
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Service service = context.getBean(Service.class);
        for(int i = 0 ; i < 60 ; i ++){
            service.biz();
            Thread.sleep(5000);
        }
    }
}