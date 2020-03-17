package com.moheqionglin.remotecontroll.test;

import com.moheqionglin.remotecontroll.EnableRemoteControll;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName : Config
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:16
 */
@Configurable
@ComponentScan(basePackages = "com.moheqionglin.remotecontroll.test")
@EnableRemoteControll
public class Config {
}