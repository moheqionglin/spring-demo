package com.moheqionglin;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Profile("unit-test")
@Configuration
@ComponentScan(basePackages = {"com.moheqionglin"}, excludeFilters={@ComponentScan.Filter(type= FilterType.ANNOTATION,value=Configuration.class)})
@EnableTransactionManagement
public class AppTestConfig {

    @Bean
    public String name(){
        return "xx";
    }
}