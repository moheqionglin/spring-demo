package com.moheqionglin.con;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ImportBean implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        for (String b : beanDefinitionRegistry.getBeanDefinitionNames()) {
            System.out.println(b);
        }
        System.out.println("==============||===============");
    }
}
