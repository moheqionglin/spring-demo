package com.moheqionglin.dubbo.startup.xml;

import com.moheqionglin.dubbo.common.UserService;
import com.moheqionglin.dubbo.common.UserServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * @ClassName : Config
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:13
 */
@Configurable
@ImportResource("classpath:dubbo/willy-app-provider.xml") //导入xml配置项
public class ProviderConfig {
    @Bean
    public UserService userServiceImpl(){
        return new UserServiceImpl();
    }
}