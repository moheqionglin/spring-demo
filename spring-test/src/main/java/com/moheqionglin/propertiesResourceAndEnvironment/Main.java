package com.moheqionglin.propertiesResourceAndEnvironment;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:32 AM
 */
public class Main {
    public static void main(String[] args) {


        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig1.class);


        SimpleBean simpleBean = (SimpleBean) applicationContext.getBean("simpleBean");
        System.out.println(simpleBean);

    }
}