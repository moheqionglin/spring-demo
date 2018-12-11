package com.moheqionglin.aop.warnning3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)  //使用CG-Lib的可以基于类
@ComponentScan(basePackages = {"com.moheqionglin.aop.warnning3"})
public class JavaConfig {


}