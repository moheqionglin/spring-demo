package com.moheqionglin.dynamicParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 15:35
 */
@Component
public class DubboService {

    @Autowired
    private BizService service;

    public DubboService(){
        System.out.println("DubboService init");
    }

    public String queryPoint(String arg){
        String appid = DubboContext.getAppid();
        return "";
    }
}