package com.moheqionglin.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 1:49 PM
 */
@Configuration
public class JavaConfig {

    @Bean
    public SpringLogger springLogger(){
        return new SpringLogger();
    }
}