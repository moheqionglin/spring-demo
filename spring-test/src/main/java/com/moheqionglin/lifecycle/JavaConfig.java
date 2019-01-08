package com.moheqionglin.lifecycle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wanli.zhou
 * @description
 * @time 03/01/2019 3:40 PM
 */
@Configuration
@ComponentScan(basePackages = "com.moheqionglin.lifecycle")
@PropertySource("classpath:lifecycle.properties")
public class JavaConfig {

}