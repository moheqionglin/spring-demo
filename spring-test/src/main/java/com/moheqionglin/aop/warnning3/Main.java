package com.moheqionglin.aop.warnning3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        SimpleBean simpleBean = (SimpleBean) annotationConfigApplicationContext.getBean("simpleBean");
        simpleBean.print();
        simpleBean.print1();
        System.out.println("同一个类内部调用被AOP方法");
        simpleBean.print1();
        System.out.println();
        System.out.println("类外部调用被AOP方法");
        SimpleBean1 simpleBean1 = (SimpleBean1) annotationConfigApplicationContext.getBean("simpleBean1");
        simpleBean1.print();
    }
}