package com.moheqionglin.centerconfig.test;

import com.moheqionglin.centerconfig.client.CenterConfigClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.ClassPathResource;

/**
 * @ClassName : Config
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 19:29
 */
@Configurable
@ComponentScan(basePackages = "com.moheqionglin.centerconfig.test")
public class Config {
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(Environment environment) {
        CenterConfigClient p = new CenterConfigClient();
        String activeProfile = environment.getActiveProfiles()[0];
        System.out.println("=====" + activeProfile + "======");
        String propertiesFilename = "propertiesResourceAndEnvironment" + (activeProfile.equalsIgnoreCase("default") ? "" : "-" + activeProfile) + ".properties";

        p.setLocation(new ClassPathResource(propertiesFilename));
        return p;
    }
}