package com.moheqionglin.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 03/01/2019 3:41 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        System.out.println("\n\n\n");

//        //不管调用没有默认都是性
        LifecycleBean lifecycleBean = (LifecycleBean) applicationContext.getBean("lifecycleBean");
        lifecycleBean.print();

        System.out.println("\n\n\n");

        LifecycleBean lifecycleBean1 = (LifecycleBean) applicationContext.getBean("lifecycleBean");
        lifecycleBean1.print();
//
//        System.out.println("\n\n\n");
//
//        LifecycleProtpotypeBean lifecycleProtpotypeBean = (LifecycleProtpotypeBean) applicationContext.getBean("lifecycleProtpotypeBean");
//        lifecycleProtpotypeBean.print();

//        applicationContext.close();

        Thread.sleep(10000);
    }
}