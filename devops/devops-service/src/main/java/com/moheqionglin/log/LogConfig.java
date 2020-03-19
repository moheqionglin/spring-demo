package com.moheqionglin.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @ClassName : LogConfig
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 20:20
 */
public class LogConfig {
    static Logger log = LoggerFactory.getLogger(LogConfig.class);
    public static void init() throws IOException, JoranException {
        Resource resource = new ClassPathResource("logback.xml");
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(resource.getInputStream());
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
    }

    public static void main(String[] args) throws IOException, JoranException {
        init();
        for(int i = 0 ; i < 100; i ++){
            log.error("safsfasdf");
            log.info("asdfasf");
        }



    }
}