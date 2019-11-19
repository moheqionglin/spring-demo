package com.moheqionglin.beanpostprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-19 10:40
 */
@Component
public class ZhuRen {
    private Animal animal;

    public String name;

    public ZhuRen() {
        System.out.println("ZhuRen 构造器");
    }

    @Override
    public String toString() {
        return "ZhuRen{" +
                "animal=" + animal +
                ", name='" + name + '\'' +
                '}';
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}