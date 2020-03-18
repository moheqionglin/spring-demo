package com.moheqionglin.centerconfig.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName : Service
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 19:29
 */
@Component
public class Service {
    @Value("${center.config.db.url}")
    private String url;

    @Value("${center.config.db.username}")
    private String username;

    public void biz(){
        System.out.println("url : " + url + ", username : " + username);
    }

}