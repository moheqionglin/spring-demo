package com.moheqionglin;

import com.moheqionglin.demo.Config;
import com.moheqionglin.demo.TestDao;
import com.moheqionglin.demo.TestBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {

    @Test
    public void xmlApplicationContext(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        TestBean tb = (TestBean) applicationContext.getBean("testBean");
        tb.setName("AAAA");
        tb.print();
        System.out.println(tb);
        TestBean tb1 = (TestBean) applicationContext.getBean(TestBean.class);
        System.out.println(tb);
        System.out.println(tb == tb1);
        System.out.println(tb.equals(tb1));
    }

    @Test
    public void annotationApplicationContextTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        TestBean tb = (TestBean) applicationContext.getBean("tBean");
        System.out.println(tb);
        String names[] = applicationContext.getBeanNamesForType(TestBean.class);
        for(String name : names){
            System.out.println(name);
        }

        TestDao td = (TestDao) applicationContext.getBean("testDao");
        System.out.println(td);
    }

}
