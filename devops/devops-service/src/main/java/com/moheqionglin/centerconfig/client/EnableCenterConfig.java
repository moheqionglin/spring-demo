package com.moheqionglin.centerconfig.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(CenterConfigClient.class)
@Documented
public @interface EnableCenterConfig {

}
