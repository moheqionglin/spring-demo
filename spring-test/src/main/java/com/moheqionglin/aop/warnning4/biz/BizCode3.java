package com.moheqionglin.aop.warnning4.biz;

import com.moheqionglin.aop.warnning4.BizAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 9:17 PM
 */
@Component
public class BizCode3 implements ApplicationContextAware, BeanNameAware {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private String beanName;

    @BizAnno
    public void fun1(){
        log.info(" => [method] fun1");
        ((BizCode3)applicationContext.getBean(beanName)).fun2();
    }


    @BizAnno
    public void fun2(){
        log.info(" => [method] fun2");
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}