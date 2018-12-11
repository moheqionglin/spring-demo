package com.moheqionglin.aop.warnning;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        Children children = (Children) annotationConfigApplicationContext.getBean("children");
        children.print();


    }
}