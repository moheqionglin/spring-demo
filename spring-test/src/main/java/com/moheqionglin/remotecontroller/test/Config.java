package com.moheqionglin.remotecontroller.test;

import com.moheqionglin.remotecontroller.EnableRemoteControll;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName : Config
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:16
 */
@Configurable
@ComponentScan(basePackages = "com.moheqionglin.remotecontroller.test")
@EnableRemoteControll
public class Config {
}