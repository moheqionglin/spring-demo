package com.moheqionglin.beanpostprocessor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 13:45
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        Animal animal = (Animal) configApplicationContext.getBean("animal");
        System.out.println(animal);
        configApplicationContext.close();
    }
}