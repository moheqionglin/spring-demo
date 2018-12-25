package com.moheqionglin.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 21/12/2018 11:04 AM
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);


        TestBean testBean = (TestBean) applicationContext.getBean("testBean");

        TestBean2 testBean2 = (TestBean2) applicationContext.getBean("testBean2");

        testBean.print();

        testBean2.print();

    }
}