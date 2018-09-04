package com.moheqionglin.springjetty;

import com.moheqionglin.springjetty.controller.HelloController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "com.moheqionglin.springjetty" })
@Import({ MvcConfiguration.class })
public class AppConfiguration {
}
