package com.moheqionglin;

import com.moheqionglin.demo.Config;
import com.moheqionglin.beanpostprocessor.ExtConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@Configurable
@Import({
        Config.class,
        ExtConfig.class,
        ConfigurationImportBeanDefinitionRegistrar.class
})
@EnableAsync
public class AppConfig {
}
