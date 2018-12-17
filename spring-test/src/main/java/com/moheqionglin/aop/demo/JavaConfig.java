package com.moheqionglin.aop.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 5:04 PM
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan(basePackages = "com.moheqionglin.aop.demo")
public class JavaConfig {

}