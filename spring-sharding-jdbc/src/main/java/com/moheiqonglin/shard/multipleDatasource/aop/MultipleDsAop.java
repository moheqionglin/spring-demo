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

    /**
     *  aop 相同类不同函数间调用 无效。
     *
     * 1. 同类之间不同方法交叉调用
     *    1.1 有datasourceKey的函数 -> 有datasourceKey的函数  [相同产品]   --> OK
     *    1.2 有datasourceKey的函数 -> 有datasourceKey的函数  [不相同产品]
     *    1.3 有datasourceKey的函数 -> 没有datasourceKey的函数
     *    1.4 没有datasourceKey的函数 -> 有datasourceKey的函数
     *    1.5 没有datasourceKey的函数 -> 没有datasourceKey的函数
     *
     * 2. 不同类之间不同方法交叉调用
     *    1.1 有datasourceKey的函数 -> 有datasourceKey的函数  [相同产品]
     *    1.2 有datasourceKey的函数 -> 有datasourceKey的函数  [不相同产品]
     *    1.3 有datasourceKey的函数 -> 没有datasourceKey的函数
     *    1.4 没有datasourceKey的函数 -> 有datasourceKey的函数
     *    1.5 没有datasourceKey的函数 -> 没有datasourceKey的函数
     *
     */

    @Around("execution(public * com.moheiqonglin.shard.multipleDatasource.dao.*.*(..))")
    public Object routeAop(ProceedingJoinPoint pjp) throws Throwable {
        String originDsKey = DatasourceHolder.getDatasourceKey();

        Object[] args = pjp.getArgs();
        int dsKeyParamIndex = getDsKeyParamIndex(pjp);

        if(dsKeyParamIndex < 0){
            DatasourceHolder.clearDatasourceKey();
            return pjp.proceed();
        }else{
            DatasourceHolder.setDatasourceKey(String.valueOf(args[dsKeyParamIndex]));
        }

        Object proceed = pjp.proceed();
        DatasourceHolder.setDatasourceKey(originDsKey);
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