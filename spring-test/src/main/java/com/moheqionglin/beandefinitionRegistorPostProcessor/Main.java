package com.moheqionglin.beandefinitionRegistorPostProcessor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 14:15
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanDefinitionRegistorPostProcessorConfig.class);
        Color color = (Color) applicationContext.getBean("color");
        System.out.println(color);
        applicationContext.close();
    }

}