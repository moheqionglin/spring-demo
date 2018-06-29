package com.moheqionglin;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(Color.class);
        bd.getPropertyValues().addPropertyValue("name", "自动生产 name");
        registry.registerBeanDefinition("color", bd);
        System.out.println(importingClassMetadata.getAnnotationAttributes(Component.class.getName()));
        System.out.println("importingClassMetadata " + importingClassMetadata + ", registry" + registry + " " +  registry.getBeanDefinitionNames());
    }
}
