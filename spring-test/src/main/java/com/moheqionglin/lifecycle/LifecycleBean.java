package com.moheqionglin.lifecycle;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wanli.zhou
 * @description
 * @time 03/01/2019 3:45 PM
 */
@Component
public class LifecycleBean implements InitializingBean {

    @Value("${lifecycle.name}")
    private String name;

    @Value("${lifecycle.location}")
    private String location;

    @Value("${lifecycle.other}")
    private String other;

    public LifecycleBean(){
        System.out.println("0  ====> LifecycleBean 构造函数");
    }
    @PostConstruct
    public void init(){
        System.out.println("2 =====> LifecycleBean[@PostConstruct] PostConstruct");
    }

    public void print(){
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "LifecycleBean{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", other='" + other + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(" 3 ====>LifecycleBean[InitializingBean.afterPropertiesSet] afterPropertiesSet");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("--==4");
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}