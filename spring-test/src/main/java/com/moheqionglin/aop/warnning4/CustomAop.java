package com.moheqionglin.aop.warnning4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 9:15 PM
 */
@Aspect
@Component
public class CustomAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.moheqionglin.aop.warnning4.biz.*.*(..))")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(">>  AOP before method ");
        Object proceed = joinPoint.proceed();
        log.info(">>  AOP After method ");
        return proceed;
    }

}