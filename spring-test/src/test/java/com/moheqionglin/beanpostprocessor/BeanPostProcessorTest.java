package com.moheqionglin.beanpostprocessor;


import com.moheqionglin.AppConfig;
import com.moheqionglin.Color;
import javafx.application.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanPostProcessorTest {

    @Test
    public void springBeanPostProcessor(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Color color = (Color) applicationContext.getBean("color");
        System.out.println(color.getName());
    }
}
