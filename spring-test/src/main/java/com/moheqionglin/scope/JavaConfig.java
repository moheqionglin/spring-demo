package com.moheqionglin.scope;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author wanli.zhou
 * @description
 * @time 21/12/2018 11:04 AM
 */
@Configuration
@ComponentScan(basePackages = "com.moheqionglin.scope")
public class JavaConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PrototypeBean prototypeBean(){
        return new PrototypeBean();
    }

    @Bean
    public SingletonBean singletonBean(){
        return new SingletonBean();
    }
}