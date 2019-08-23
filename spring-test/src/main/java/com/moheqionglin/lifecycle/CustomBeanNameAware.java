package com.moheqionglin.lifecycle;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-31 10:31
 */
@Component
public class CustomBeanNameAware implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
            System.out.println("]] CustomBeanNameAware.setBeanName " + name);

    }
}