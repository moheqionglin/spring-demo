package com.moheqionglin;

import com.moheqionglin.demo.Config;
import com.moheqionglin.demo.TestDao;
import com.moheqionglin.demo.TestBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
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
