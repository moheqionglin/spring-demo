package com.moheqionglin.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-31 10:17
 */
@Component
public class CustomInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if("lifecycleBean".equalsIgnoreCase(beanName)){
            System.out.println(">>InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation "  + beanName);
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if("lifecycleBean".equalsIgnoreCase(beanName)) {
            System.out.println(">>InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation " + beanName);
        }
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        String pvstr = "";
        for(PropertyValue pv : pvs.getPropertyValues()){
            pvstr += pv.getName() + " : " + pv.getValue() + ", ";
        }
        if("lifecycleBean".equalsIgnoreCase(beanName)) {
            System.out.println(">>InstantiationAwareBeanPostProcessor.postProcessPropertyValues " + beanName + " " + pvstr);
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("lifecycleBean".equalsIgnoreCase(beanName)) {
            System.out.println(">>InstantiationAwareBeanPostProcessor.postProcessBeforeInitialization " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("lifecycleBean".equalsIgnoreCase(beanName)) {
            System.out.println(">>InstantiationAwareBeanPostProcessor.postProcessAfterInitialization " + beanName);
        }
        return bean;
    }
}