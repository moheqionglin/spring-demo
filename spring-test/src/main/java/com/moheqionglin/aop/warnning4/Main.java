package com.moheqionglin.aop.warnning4;

import com.moheqionglin.aop.warnning4.biz.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 9:19 PM
 *
 * AOP 同类内部函数调用 破解方法
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);


        BizCode1 bizCode1 = (BizCode1) applicationContext.getBean("bizCode1");
        bizCode1.fun1();

        System.out.println();

        BizCode2 bizCode2 = (BizCode2) applicationContext.getBean("bizCode2");
        bizCode2.fun1();

        System.out.println();

        BizCode3 bizCode3 = (BizCode3) applicationContext.getBean("bizCode3");
        bizCode3.fun1();

        System.out.println();

        BizCode4 bizCode4 = (BizCode4) applicationContext.getBean("bizCode4");
        bizCode4.fun1();

        System.out.println();
        OtherBiz otherBiz = (OtherBiz) applicationContext.getBean("otherBiz");
        otherBiz.print();
    }
}