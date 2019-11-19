package com.moheqionglin.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Animal{
    @Value("${application.name}")
    private String name;
    @Value("${xyadf.asdf:\\:asdf}")
    private String type;


    public Animal() {
        System.out.println("Animal(String name, String type); name = " + name + ", type = " + type);
    }

    public Animal(String name, String type) {
        this.name = name;
        this.type = type;
        System.out.println("Animal(String name, String type); name = " + name + ", type = " + type);
    }

    @PostConstruct
    public void init(){
        System.out.println("Animal post construct init ...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Animal before destroy...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private void initMethod() {
        System.out.println("Animal => initMethod");
    }

    private void destroyMethod() {
        System.out.println("Animal => destroyMethod");
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
