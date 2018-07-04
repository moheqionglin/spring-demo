package com.moheqionglin.beandefinitionRegistorPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class ColorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Color color= (Color) beanFactory.getBean("color");
        if(color == null){
            beanFactory.registerSingleton("color", new Color("color在beanfactory中为空，新建一个"));
            System.out.println("[ext]=>color在beanfactory中为空，新建一个");
        }
        color.setName("BeanFactoryPostProcessor 处理以后的 color");
        System.out.println("[ext]=>color在beanfactory中找到了一个 color，重庆名color的名字");
    }
}
