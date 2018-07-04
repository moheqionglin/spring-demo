package com.moheqionglin.con;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configurable
@PropertySource("classpath:test.properties")
@ComponentScan(basePackages = "com.qxwz.locationx.service.con")
@Import(
        {ImportBean.class,
                BaseBean.class}
)
public class Config {

//    @Bean(initMethod = "init")
//    public com.qxwz.columbus.client.spring.ProjectConfig wanliProjectConfig(){
//        com.qxwz.columbus.client.spring.ProjectConfig p = new com.qxwz.columbus.client.spring.ProjectConfig();
//        p.setProject("locationx");
//        return p;
//    }

    @Bean
    public DataBasePropertyPlaceholderConfigurer dataBasePropertyPlaceholderConfigurer(){
        return new DataBasePropertyPlaceholderConfigurer();
    }
}
