package com.moheqionglin.simpleDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 5:33 PM
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        TestBean tb = (TestBean) applicationContext.getBean("tBean");
        System.out.println(tb);
        System.out.println(tb.getAts());
        String names[] = applicationContext.getBeanNamesForType(TestBean.class);
        for(String name : names){
            System.out.println(name);
        }

        TestDao td = (TestDao) applicationContext.getBean("testDao");
        System.out.println(td);

        Dog dog = (Dog) applicationContext.getBean("Doggg");
        System.out.println(dog);

    }
}