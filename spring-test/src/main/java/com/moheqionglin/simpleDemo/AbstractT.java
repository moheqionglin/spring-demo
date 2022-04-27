package com.moheqionglin.simpleDemo;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractT implements IClass{
    protected void overprint(){
        System.out.println("AbstractT");
    }
}
