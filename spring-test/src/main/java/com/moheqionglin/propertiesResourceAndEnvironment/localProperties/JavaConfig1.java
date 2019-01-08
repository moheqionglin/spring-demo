package com.moheqionglin.propertiesResourceAndEnvironment.localProperties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.ClassPathResource;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 11:10 AM
 */
@Configuration
@Import(JavaConfig.class)
public class JavaConfig1 {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(Environment environment) {
        PropertyPlaceholderConfigurer p = new PropertyPlaceholderConfigurer();

        String activeProfile = environment.getActiveProfiles()[0];
        System.out.println("=====" + activeProfile + "======");
        String propertiesFilename = "propertiesResourceAndEnvironment" + (activeProfile.equalsIgnoreCase("default") ? "" : "-" + activeProfile) + ".properties";

        MutablePropertySources mutablePropertySources = new MutablePropertySources();
        p.setLocation(new ClassPathResource(propertiesFilename));
        return p;
    }
}