package com.moheqionglin.beandefinitionRegistorPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class ColorBeanDefinitionRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinition colorDefinition = registry.getBeanDefinition("color");
        if(colorDefinition == null){
            colorDefinition = new GenericBeanDefinition();
            registry.registerBeanDefinition("color", colorDefinition);
            ((GenericBeanDefinition) colorDefinition).setBeanClass(Color.class);
            colorDefinition.getPropertyValues().addPropertyValue("name", "BeanDefinitionRegistryPostProcessor中没有color定义->经过BeanDefinitionRegistryPostProcessor->postProcessBeanDefinitionRegistry处理的 name");
            System.out.println("[ext]=>BeanDefinitionRegistryPostProcessor中没有color定义->经过BeanDefinitionRegistryPostProcessor->postProcessBeanDefinitionRegistry处理的 name");
            return;
        }
        System.out.println("[ext]=>BeanDefinitionRegistryPostProcessor发现了color定义->经过BeanDefinitionRegistryPostProcessor->postProcessBeanDefinitionRegistry处理的 name");
        colorDefinition.getPropertyValues().addPropertyValue("name", "->经过BeanDefinitionRegistryPostProcessor->postProcessBeanDefinitionRegistry处理的 name");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Color color= (Color) beanFactory.getBean("color");
        if(color == null){
            beanFactory.registerSingleton("color", new Color("color在ColorBeanDefinitionRegisterPostProcessor中为空，新建一个->postProcessBeanFactory"));
            System.out.println("[ext]=>color在ColorBeanDefinitionRegisterPostProcessor中为空，新建一个->postProcessBeanFactory");
        }
        color.setName("BeanDefinitionRegistryPostProcessor->postProcessBeanFactory 处理以后的 color");
        System.out.println("[ext]=>color在BeanDefinitionRegistryPostProcessor->postProcessBeanFactory 中找到了一个 color，重庆名color的名字");
    }
}
