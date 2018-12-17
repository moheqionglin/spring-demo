package com.moheqionglin.aop.warnning;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 *
 * http://moheqionglin.com/site/serialize/02008005001/detail.html
 *
 * @EnableAspectJAutoProxy(proxyTargetClass = true) 使用JDK代理还是使用CGlib代理实现AOP， jdk代理的AOP只能拦截实现接口的切点， cglib没有这个限制
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        IInterface children = (IInterface) annotationConfigApplicationContext.getBean("children");
        children.iprint();


    }
}