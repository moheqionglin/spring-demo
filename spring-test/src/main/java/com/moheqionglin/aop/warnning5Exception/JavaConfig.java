package com.moheqionglin.aop.warnning5Exception;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 11:15 AM
 */
@Configuration
@ComponentScan(basePackages = "com.moheqionglin.aop.warnning5Exception")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JavaConfig {

}