package com.moheiqonglin.shard.multipleDatasource.aop;

import com.moheiqonglin.shard.multipleDatasource.DatasourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:16 PM
 */
@Component
@Aspect
public class MultipleDsAop {

    @Around("execution(public * com.moheiqonglin.shard.multipleDatasource.*.*(..))")
    public Object routeAop(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = MethodSignature.class.cast(pjp.getSignature());

        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = pjp.getArgs();
        int i = 0 ;
        for(Parameter parameter : parameters){
            for(Annotation annotation : parameter.getAnnotations()){
                if(annotation instanceof DatasourceKey){
                    DatasourceHolder.setDatasourceKey(String.valueOf(args[i]));
                    System.out.println("-->");

                }
            }
            i ++;
        }

        Object proceed = pjp.proceed();
        DatasourceHolder.clearDatasourceKey();

        return proceed;
    }

}