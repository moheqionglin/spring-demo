package com.moheqionglin.beandefinitionRegistorPostProcessor;

import org.springframework.stereotype.Component;

@Component
public class Color {
    private String name;

    public Color(String name) {
        this.name = name;
        System.out.println("-> color 构造函数" + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Color{" +
                "name='" + name + '\'' +
                '}';
    }
}
