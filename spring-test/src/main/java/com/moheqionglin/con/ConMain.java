package com.moheqionglin.con;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConMain {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        for(String n: applicationContext.getBeanDefinitionNames()){
            System.out.println(n);
        }
        BaseBean bb = (BaseBean) applicationContext.getBean("baseBean");

        System.out.println(bb);
    }
}
