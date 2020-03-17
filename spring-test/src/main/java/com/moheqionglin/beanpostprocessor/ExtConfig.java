package com.moheqionglin.beanpostprocessor;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@Configurable
@ComponentScan(basePackages = {"com.moheqionglin.beanpostprocessor"})
@PropertySource("classpath:application.properties")
public class ExtConfig {

//    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public Animal animal(){
        return new Animal("狗", "哺乳类");
    }
}
