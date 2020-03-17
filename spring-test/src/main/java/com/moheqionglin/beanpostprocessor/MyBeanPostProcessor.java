package com.moheqionglin.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(">> BeforeInitialization " + bean + " " + beanName);
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        System.out.println(">> AfterInitialization " + bean + " " + beanName);
        if(bean instanceof Animal){
            Animal animal = ((Animal) bean);
            animal.setName("BeanProstProcessor ->" + animal.getName());
            animal.setType("BeanProstProcessor ->" + animal.getType());
            System.out.println("== >> Get Bean animal " + bean);
            return animal;
        }
        return bean;
    }
}
