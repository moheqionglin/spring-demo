package com.moheqionglin.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-31 10:32
 */
@Component
public class CustomBeanFactoryAware implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("|| CustomBeanFactoryAware.setBeanFactory " + beanFactory.toString());
    }
}