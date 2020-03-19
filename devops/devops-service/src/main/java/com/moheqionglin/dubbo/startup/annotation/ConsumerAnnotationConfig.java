package com.moheqionglin.dubbo.startup.annotation;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName : ConsumerAnnotationConfig
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 17:44
 */
@EnableDubbo(scanBasePackages = "com.moheqionglin.dubbo.common")
@PropertySource("classpath:dubbo/dubbo-consumer.properties")
public class ConsumerAnnotationConfig {
    @Bean
    public ConsumerAction consumerAction(){
        return new ConsumerAction();
    }
}