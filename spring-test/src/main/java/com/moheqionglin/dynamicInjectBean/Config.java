package com.moheqionglin.dynamicInjectBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wanli.zhou
 * @description
 * @time 11/12/2018 4:36 PM
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.moheqionglin.dynamicInjectBean")
public class Config {
}