package com.moheqionglin.beandefinitionRegistorPostProcessor;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;


@Component
public class ColorImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        BeanDefinition colorDefinition = registry.getBeanDefinition("color");
        if(colorDefinition == null){
            colorDefinition = new GenericBeanDefinition();
            registry.registerBeanDefinition("color", colorDefinition);
            ((GenericBeanDefinition) colorDefinition).setBeanClass(Color.class);
            colorDefinition.getPropertyValues().addPropertyValue("name", "ImportBeanDefinitionRegistrar中没有color定义->经过ImportBeanDefinitionRegistrar处理的 name");
            System.out.println("[ext]=>ImportBeanDefinitionRegistrar中没有color定义->经过ImportBeanDefinitionRegistrar处理的 name");
            return;
        }
        System.out.println("[ext]=>ImportBeanDefinitionRegistrar发现了color定义->经过ImportBeanDefinitionRegistrar处理的 name");
        colorDefinition.getPropertyValues().addPropertyValue("name", "->经过ImportBeanDefinitionRegistrar处理的 name");
//        System.out.println(importingClassMetadata.getAnnotationAttributes(Component.class.getName()));
//        System.out.println("importingClassMetadata " + importingClassMetadata + ", registry" + registry + " " +  registry.getBeanDefinitionNames());
    }
}
