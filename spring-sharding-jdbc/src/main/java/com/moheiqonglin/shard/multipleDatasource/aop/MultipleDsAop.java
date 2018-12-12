package com.moheiqonglin.shard.multipleDatasource.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:16 PM
 */
@Component
@Aspect
public class MultipleDsAop {

    @Around("execution(public * com.moheiqonglin.shard.multipleDatasource.dao.*.*(..))")
    public Object routeAop(ProceedingJoinPoint pjp) throws Throwable {
        DatasourceHolder.clearDatasourceKey();

        Object[] args = pjp.getArgs();

        int dsKeyParamIndex = getDsKeyParamIndex(pjp);
        if(dsKeyParamIndex < 0){
            return pjp.proceed();
        }

        DatasourceHolder.setDatasourceKey(String.valueOf(args[dsKeyParamIndex]));
        Object proceed = pjp.proceed();

        return proceed;
    }

    private int getDsKeyParamIndex(ProceedingJoinPoint pjp){
        MethodSignature signature = MethodSignature.class.cast(pjp.getSignature());
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int paramsAnnotationLength = parameterAnnotations.length;
        if(paramsAnnotationLength == 0){
            return -1;
        }

        for(int i = 0; i < paramsAnnotationLength; i ++){
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            int length = parameterAnnotation.length;
            if(length == 0){
                continue;
            }
            for(int j = 0 ; j < length ; j ++){
                if(parameterAnnotations[i][j] instanceof DatasourceKey){
                    return i;
                }
            }
        }

        return -1;
    }

}