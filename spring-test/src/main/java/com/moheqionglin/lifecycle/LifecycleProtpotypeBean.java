package com.moheqionglin.lifecycle;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wanli.zhou
 * @description
 * @time 04/01/2019 4:19 PM
 */
@Component
@Scope("prototype")
public class LifecycleProtpotypeBean implements InitializingBean {

    @Value("${lifecycle.name}")
    private String name;

    @Value("${lifecycle.location}")
    private String location;

    @Value("${lifecycle.other}")
    private String other;


    public LifecycleProtpotypeBean(){
        System.out.println("0  ====> LifecycleProtpotypeBean 构造函数");
    }
    public void print(){
        System.out.println("LifecycleProtpotypeBean -- > print" + toString());
    }
    @PostConstruct
    public void init(){
        System.out.println("2 =====> LifecycleProtpotypeBean[@PostConstruct] PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(" 3 ====>LifecycleProtpotypeBean[InitializingBean.afterPropertiesSet] afterPropertiesSet");
    }

    @Override
    public String toString() {
        return "LifecycleProtpotypeBean{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}