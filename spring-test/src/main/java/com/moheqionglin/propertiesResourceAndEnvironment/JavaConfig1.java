package com.moheqionglin.propertiesResourceAndEnvironment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer(Environment environment) {

        String activeProfile = environment.getActiveProfiles()[0];
        System.out.println(activeProfile);
        String propertiesFilename = "propertiesResourceAndEnvironment-" + (activeProfile.equalsIgnoreCase("default") ? "" : activeProfile) + ".properties";
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        MutablePropertySources mutablePropertySources = new MutablePropertySources();
        configurer.setLocation(new ClassPathResource(propertiesFilename));
        return configurer;
    }
}