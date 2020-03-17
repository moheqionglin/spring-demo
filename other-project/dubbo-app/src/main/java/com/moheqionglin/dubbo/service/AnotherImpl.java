package com.moheqionglin.dubbo.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wanli.zhou
 * @description
 * @time 07/01/2019 11:07 AM
 */
@Component
public class AnotherImpl implements Another {

    @PostConstruct
    public void init(){
        System.out.println("-AnotherImpl->");
    }
    @Override
    public String print(String name) {
        String s = "Another " + name;
        System.out.println(s);
        return s;
    }
}