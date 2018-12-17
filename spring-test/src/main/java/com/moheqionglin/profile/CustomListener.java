package com.moheqionglin.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 13/12/2018 11:25 PM
 */
@Component
public class CustomListener implements ApplicationContextInitializer {

    @Value("${profile}")
    private String activeProfile;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        environment.setActiveProfiles(activeProfile);
    }
}