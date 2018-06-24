package com.moheqionglin;

import com.moheqionglin.config.Config;
import com.moheqionglin.dao.TestDao;
import com.moheqionglin.services.TestBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        TestBean tb = (TestBean) context.getBean("tBean");
        System.out.println(tb);
        String names[] = context.getBeanNamesForType(TestBean.class);
        for(String name : names){
            System.out.println(name);
        }

        TestDao td = (TestDao) context.getBean("testDao");
        System.out.println(td);
    }

    public void xmlApplicationContext(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        TestBean tb = (TestBean) context.getBean("testBean");
        tb.setName("AAAA");
        tb.print();
        System.out.println(tb);
        TestBean tb1 = (TestBean) context.getBean(TestBean.class);
        System.out.println(tb);
        System.out.println(tb == tb1);
        System.out.println(tb.equals(tb1));
    }
}
