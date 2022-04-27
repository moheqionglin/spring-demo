package com.moheqionglin.simpleDemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class BeanDynamicRegister implements BeanFactoryAware {

    private BeanFactory beanFactory;
    public void dynamicInject(){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        defaultListableBeanFactory.registerSingleton("Doggg", new Dog("万里", "男"));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("beanFactory instanceof DefaultListableBeanFactory = >>" + (beanFactory instanceof DefaultListableBeanFactory));
        this.beanFactory = beanFactory;
    }
}
