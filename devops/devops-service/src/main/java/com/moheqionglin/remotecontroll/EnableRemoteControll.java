package com.moheqionglin.remotecontroll;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName : EnableRemoteControll
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 15:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RemoteCommandServer.class)
public @interface EnableRemoteControll {


}