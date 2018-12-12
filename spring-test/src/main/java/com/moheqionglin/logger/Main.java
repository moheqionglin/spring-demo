package com.moheqionglin.logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 1:49 PM
 */
public class Main {

    //http://logback.qos.ch/manual/introduction.html
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);


        SpringLogger springLogger = (SpringLogger) applicationContext.getBean("springLogger");

        springLogger.printLog();

    }
}