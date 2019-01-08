package com.moheqionglin.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 03/01/2019 3:42 PM
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if(s.equalsIgnoreCase("lifecycleBean")){
            System.out.println("1 ==>lifecycleBean[BeanPostProcessor.postProcessBeforeInitialization]" + s + "  " + o.getClass() + " " + o);
        }
        if(s.equalsIgnoreCase("LifecycleProtpotypeBean")){
            System.out.println("1 ==>LifecycleProtpotypeBean[BeanPostProcessor.postProcessBeforeInitialization]" + s + "  " + o.getClass() + " " + o);
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if(s.equalsIgnoreCase("lifecycleBean")){
            System.out.println(" 4 ==>lifecycleBean[CustomBeanPostProcessor.postProcessAfterInitialization]" + s + "  " + o.getClass() + " " + o);
        }
        if(s.equalsIgnoreCase("LifecycleProtpotypeBean")){
            System.out.println(" 4 ==>LifecycleProtpotypeBean[CustomBeanPostProcessor.postProcessAfterInitialization]" + s + "  " + o.getClass() + " " + o);
        }
        return o;
    }
}