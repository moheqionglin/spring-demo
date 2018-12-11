package com.moheqionglin.aop.warnning;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:01 PM
 */
@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true)  //使用CG-Lib的可以基于类
@EnableAspectJAutoProxy//会有异常：  因为spring默认使用 jdk的动态代理， 必须基于接口
@ComponentScan(basePackages = {"com.moheqionglin.aop.warnning"})
public class JavaConfig {


}