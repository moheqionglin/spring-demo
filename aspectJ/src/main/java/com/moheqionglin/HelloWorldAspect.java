package com.moheqionglin;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
@Aspect
public class HelloWorldAspect {

    @Pointcut("execution(* com.moheqionglin.HelloWorld.sayChinese(..))")
    public void jointPoint(){
    }

    @Before("jointPoint()")
    public void before(){
        System.out.println("=< 之前");
    }


    @After("jointPoint()")
    public void after(){
        System.out.println("=> 之后");
    }

}
