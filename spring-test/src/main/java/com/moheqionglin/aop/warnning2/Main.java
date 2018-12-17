package com.moheqionglin.aop.warnning2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 *
 * http://moheqionglin.com/site/serialize/02008005002/detail.html
 * 使用 @Aroud形式增强代码的时候， 有两个限制
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        SimpleBean simpleBean = (SimpleBean) annotationConfigApplicationContext.getBean("simpleBean");
        simpleBean.print();
    }
}