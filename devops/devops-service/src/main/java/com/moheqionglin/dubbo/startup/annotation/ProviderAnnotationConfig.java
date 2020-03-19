package com.moheqionglin.dubbo.startup.annotation;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName : ProviderConfig
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 17:44
 */
@Configurable
@EnableDubbo(scanBasePackages = "com.moheqionglin.dubbo.common")
@PropertySource("classpath:dubbo/dubbo-provider.properties")
public class ProviderAnnotationConfig {

}