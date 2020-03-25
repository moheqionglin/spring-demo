package com.moheqionglin.globalexception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class GlobalExceptionAop {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(throwing="error" , pointcut = "execution(public * com.moheqionglin.globalexception.*.*(..))")
    public void process1(JoinPoint jp, Throwable error) throws GlobalBizException {
        if(error instanceof GlobalBizException){
            log.error(jp.getSignature().toString() + " error info :  ", error);
            throw (GlobalBizException)error;
        }else {
            log.error(jp.getSignature().toString() + " error info :  ", error);
            throw new GlobalBizException(-1, "Unknow error!");
        }
    }


}
