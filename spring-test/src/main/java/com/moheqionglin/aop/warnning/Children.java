package com.moheqionglin.aop.warnning;

import javafx.scene.Parent;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 3:03 PM
 */
@Component
public class Children extends Parent implements IInterface{
    protected void print(){
        System.out.println("Children --> ");
    }

    public void otherPublicMethod(){
        System.out.println("otherPublicMethod ->");
    }

    @Override
    public void iprint() {
        System.out.println("Children -> iprint >>");
    }
}