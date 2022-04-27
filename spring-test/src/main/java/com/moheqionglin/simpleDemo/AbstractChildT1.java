package com.moheqionglin.simpleDemo;

import org.springframework.stereotype.Component;

@Component
public class AbstractChildT1 extends AbstractT{
    protected void overprint(){
        System.out.println("AbstractChildT1");
    }

    @Override
    public String getTaskCode() {
        return "1";
    }
}
