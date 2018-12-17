package com.moheqionglin.aop.warnning5Exception;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 11:15 AM
 *
 * 验证 AOP 的 @Around直接 throw exception和 try-catch异常区别
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        Biz biz = (Biz) applicationContext.getBean("biz");

        biz.bizFunc();
    }
}