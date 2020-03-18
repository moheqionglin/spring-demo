package com.moheqionglin.propertiesResourceAndEnvironment.configCenterProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:27 AM
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:propertiesResourceAndEnvironment.properties"),
        @PropertySource("classpath:propertiesResourceAndEnvironment-dev.properties"),
        @PropertySource("classpath:propertiesResourceAndEnvironment-prod.properties")
})
public class JavaConfig {


    @Value("${env}")
    private String env;

    @Value("${config.value}")
    private String configValue;

    @Value("${config.value1}")
    private String configValue1;


    @Profile("dev")
    @Bean(name = "simpleBean")
    public SimpleBean simpleBean(Environment environment){

        System.out.println(Arrays.asList(environment.getActiveProfiles()));
        System.out.println(Arrays.asList(environment.getDefaultProfiles()));
        System.out.println(environment.getProperty("config.value"));
        return new SimpleBean("dev", configValue, configValue1);
    }

    @Profile("default")
    @Bean(name = "simpleBean")
    public SimpleBean simpleBean2(Environment environment){
        System.out.println(Arrays.asList(environment.getActiveProfiles()));
        System.out.println(Arrays.asList(environment.getDefaultProfiles()));
        System.out.println(environment.getProperty("config.value"));
        return new SimpleBean("default", configValue, configValue1);
    }

    @Profile("prod")
    @Bean(name = "simpleBean")
    public SimpleBean simpleBean1(Environment environment){
        System.out.println(Arrays.asList(environment.getActiveProfiles()));
        System.out.println(Arrays.asList(environment.getDefaultProfiles()));
        System.out.println(environment.getProperty("config.value"));
        return new SimpleBean("prod", configValue, configValue1);
    }


}