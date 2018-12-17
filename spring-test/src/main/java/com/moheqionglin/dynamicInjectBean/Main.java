package com.moheqionglin.dynamicInjectBean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 5:38 PM
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        if(applicationContext.containsBean("dynamicBean")){
            System.out.println("contain DynamicBean.");
        }else{
            System.out.println("NO contain DynamicBean!!!\n\n\n");
        }

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicBean.class);
        beanDefinitionBuilder.setInitMethodName("init");
        beanDefinitionBuilder.setDestroyMethodName("destory");
        beanDefinitionBuilder.addPropertyValue("var", "运行时注入");

        DefaultListableBeanFactory defaultListableBeanFactory = ((AnnotationConfigApplicationContext) applicationContext).getDefaultListableBeanFactory();

//        defaultListableBeanFactory.autowireBean(applicationContext.getBean("testDao"));
        defaultListableBeanFactory.registerBeanDefinition("dynamicBean", beanDefinitionBuilder.getBeanDefinition());

        DynamicBean test = (DynamicBean) applicationContext.getBean("dynamicBean");
        test.print();

        ((AnnotationConfigApplicationContext) applicationContext).close();

    }
}