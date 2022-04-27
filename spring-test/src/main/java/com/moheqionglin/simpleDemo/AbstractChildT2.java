package com.moheqionglin.simpleDemo;

import org.springframework.stereotype.Component;

@Component
public class AbstractChildT2 extends AbstractT{
    protected void overprint(){
        System.out.println("AbstractChildT2");
    }

    @Override
    public String getTaskCode() {
        return "1";
    }
}
