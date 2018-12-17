package com.moheqionglin.aop.demo;

import com.moheqionglin.aop.demo.dao.Dao1;
import com.moheqionglin.aop.demo.dao.Dao2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 5:26 PM
 */
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        Dao1 dao1 = (Dao1) applicationContext.getBean("dao1");

        Dao2 dao2 = (Dao2) applicationContext.getBean("dao2");

        String key = "CASTER";

        System.out.println("\n====sameYou2YouSameProduct=>>\n");
        dao1.sameYou2YouSameProduct(key);
        System.out.println("\n====sameYou2YouDiffProduct=>>\n");
        dao1.sameYou2YouDiffProduct(key);
        System.out.println("\n====sameYou2WuSameProduct=>>\n");
        dao1.sameYou2WuSameProduct(key);
        System.out.println("\n====sameWu2YouSameProduct=>>\n");
        dao1.sameWu2YouSameProduct(key);
        System.out.println("\n====sameWu2WuSameProduct=>>\n");
        dao1.sameWu2WuSameProduct(key);

        System.out.println("\n====diffYou2YouSameProduct=>>\n");
        dao2.diffYou2YouSameProduct(key);
        System.out.println("\n====diffYou2YouDiffProduct=>>\n");
        dao2.diffYou2YouDiffProduct(key);
        System.out.println("\n====diffYou2WuSameProduct=>>\n");
        dao2.diffYou2WuSameProduct(key);
        System.out.println("\n====diffWu2YouSameProduct=>>\n");
        dao2.diffWu2YouSameProduct(key);
        System.out.println("\n====diffWu2WuDiffProduct=>>\n");
        dao2.diffWu2WuDiffProduct(key);



    }
}
