package com.moheqionglin.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 1:45 PM
 */
@Component
public class SpringLogger {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void printLog(){
        log.debug("===DEBUG====");
        log.info("===INFO====");
        log.error("===ERROR====");

        try{
            int a = 1 / 0;
        }catch (Throwable e){
            log.warn("exp:{}", e.getMessage(), e);
            log.error("exp:{}", e);
        }
    }

}