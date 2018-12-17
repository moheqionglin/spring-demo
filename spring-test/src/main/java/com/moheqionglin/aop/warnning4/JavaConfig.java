package com.moheqionglin.aop.warnning4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 9:14 PM
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan(basePackages = "com.moheqionglin.aop.warnning4")
public class JavaConfig {



}