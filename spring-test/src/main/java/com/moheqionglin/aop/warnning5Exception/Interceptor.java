package com.moheqionglin.aop.warnning5Exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 11:17 AM
 */
@Aspect
@Component
public class Interceptor {

    @Around("execution(* com.moheqionglin.aop.warnning5Exception.Biz.*(..))")
    public Object interceptor(ProceedingJoinPoint joinPoint)  {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;

    }
}