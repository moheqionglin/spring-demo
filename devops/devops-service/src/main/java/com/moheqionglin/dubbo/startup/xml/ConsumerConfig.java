package com.moheqionglin.dubbo.startup.xml;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ImportResource;

/**
 * @ClassName : ConsumerConfig
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:38
 */
@Configurable
@ImportResource("classpath:dubbo/willy-app-consumer.xml")
public class ConsumerConfig {

}