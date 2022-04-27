package com.moheqionglin.aop.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 5:18 PM
 */
@Aspect
@Component
public class CustomAop {

    @Around("execution(* com.moheqionglin.aop.demo.dao.*.*(..))")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        //缓存上一步的现场
        String originKey = ContextHolder.getCache();

        MethodSignature signature = MethodSignature.class.cast(joinPoint.getSignature());
        Method method = signature.getMethod();

        String key = joinPoint.getArgs()[0].toString();

//        ContextHolder.clearCache();


        if(!method.isAnnotationPresent(ValidCheck.class)){
            Object process1 = joinPoint.proceed();
            //恢复现场
//            ContextHolder.setCache(originKey);
            return process1;
        }

        ContextHolder.setCache(key);
        Object proceed = joinPoint.proceed();
        ContextHolder.setCache(originKey);
        return proceed;

    }
}