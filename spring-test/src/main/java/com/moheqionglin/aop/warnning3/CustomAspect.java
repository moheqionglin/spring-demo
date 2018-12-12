package com.moheqionglin.aop.warnning3;

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

    @Around("execution( * com.moheqionglin.aop.warnning3.*.*(..)) && @annotation(simpleValid)")
    public Object process(ProceedingJoinPoint joinPoint, SimpleValid simpleValid) throws Throwable {
        System.out.println("Before>>>>");
        Object proceed = joinPoint.proceed();
        System.out.println("After<<<<<");
        return proceed;
    }
}