package com.moheqionglin.dynamicInjectBean;

import com.moheqionglin.simpleDemo.Config;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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
        beanDefinitionBuilder.addPropertyReference("testDao", "testDao");

        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();

        DynamicBean dynamicBean = new DynamicBean();
        factory.autowireBean(dynamicBean);
        factory.initializeBean(dynamicBean, DynamicBean.class.getCanonicalName());
        ((ConfigurableListableBeanFactory)factory).registerSingleton("dynamicBean", dynamicBean);

        DynamicBean test = (DynamicBean) applicationContext.getBean("dynamicBean");
        test.print();
    }
}