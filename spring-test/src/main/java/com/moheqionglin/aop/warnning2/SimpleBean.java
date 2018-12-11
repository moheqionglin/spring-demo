package com.moheqionglin.aop.warnning2;

import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:03 PM
 */
@Component
public class SimpleBean{

    @SimpleValid("void valid")
    protected void print(){
        System.out.println("SimpleBean --> ");
    }

}