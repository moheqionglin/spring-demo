package com.moheqionglin.aop.warnning3;

import com.moheqionglin.aop.warnning3.child.ChildClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 * http://moheqionglin.com/site/serialize/02008005003/detail.html
 * AOP 类内部调用无效
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        SimpleBean simpleBean = (SimpleBean) annotationConfigApplicationContext.getBean("simpleBean");
//        simpleBean.print();
        System.out.println("同一个类内部调用被AOP方法");
        simpleBean.print1();
        System.out.println();
        System.out.println("类外部调用被AOP方法");
        SimpleBean1 simpleBean1 = (SimpleBean1) annotationConfigApplicationContext.getBean("simpleBean1");
        simpleBean1.print();

        ChildClass childClass = (ChildClass) annotationConfigApplicationContext.getBean("childClass");
        childClass.print();
    }
}