package com.moheqionglin.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author wanli.zhou
 * @description
 * @time 13/12/2018 11:12 PM
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String url = "http://www.mockhttp.cn/mock/properties";
//        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("profile.properties"));
        Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();


        applicationContext.getEnvironment().setActiveProfiles(properties.getProperty("profile"));
        applicationContext.register(JavaConfig.class);
        applicationContext.refresh();
        IConsumer consumer = (IConsumer) applicationContext.getBean("consumer");

        consumer.consumer();
    }
}