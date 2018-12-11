package com.moheqionglin.aop.warnning2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:04 PM
 */
@Aspect
@Component
public class CustomAspect {

    @Around("execution(public * com.moheqionglin.aop.warnning2.*.*(..)) && @annotation(simpleValid)")
    public void process(SimpleValid simpleValid, ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before>>>>");
        joinPoint.proceed();
        System.out.println("After<<<<<");
    }
}